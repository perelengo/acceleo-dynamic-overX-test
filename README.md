# acceleo-dynamic-overX-test
Project with examples of the dynamic overriding and overloading functionality added to acceleo.

# org.eclipse.acceleo.module.sample
Prints all components names in the model, using the template and queries defined in the module org.eclipse.acceleo.module.dynamic_override_main

# org.eclipse.acceleo.module.dynamic_override_main

Defines two queries to print a component name based on the stereotypes applied to the component:

		[comment 		
						when it has no stereotype, prints the name, otherwise invokes another query that handles the printing of the name.
						This is to test query dynamic overriding
		/]
		[query public getName(arg : uml::NamedElement) : String = 
			let stereotype:Stereotype=arg.getAppliedStereotypes()->any(true) in 
			if(stereotype.oclIsUndefined()) then
				arg.name
			else
				arg.getName(stereotype.getStereotypeMetaClass()) 
			endif
		/]
		
		[comment 		
						we print (any stereotype) to show that we are invoking this template 
		/]
		[query public getName( arg : NamedElement, stereotype : OclAny ) : String = 
			arg.name+' (any stereotype)'
		/]



Also defines a template to print a component name:

	[template public printName(arg : Component)]
	
		[if(arg.getAppliedStereotypes()->size()=0)] 
			[comment 	
				when the component has no applied stereotypes, invokes the query getName on the component to get its name and print it 
			/]
			
			0 Component name: [arg.getName()/]
		[else]
			[comment 	
				when the component has applied stereotypes, gets the stereotype name and prints it 
				followed by the result of invoking the template getName on the component to get its name and print it 
				This is to test template dynamic overriding 			
			 /]
			  			
			[let stereotype:Stereotype=arg.getAppliedStereotypes()->any(true)] 
			1 [stereotype.getStereotypeMetaClass().eGet("name")/] [arg.printName(stereotype.getStereotypeMetaClass())/]
			[/let]	
		[/if]
	[/template]

	
# org.eclipse.acceleo.module.dynamic_override_entity

Defines the dynamic overriding query and template.
	
	[comment
						when the stereotype applied to a component corresponds to UMLStandardProfile::Entity
						print its name followed by (entity stereotype)
	/]
	[query public getName( arg : NamedElement, stereotype : Entity ) : String = 
	arg.name+' (entity stereotype)'
	/]


	[comment
		when the stereotype applied to a component corresponds to UMLStandardProfile::Entity
		print (overriden default printName) followed by its getName query invocation results (in this case the query also is dynamically overriden)
	/]	
	[template public printName( arg : Component , default : Entity)]
		Component name (overriden default printName): [arg.getName(default)/]
	[/template]
	
	
# Test model
Has three components:

Component 1 is stereotyped UMLStandardProfile::Auxiliary

Component 2 is stereotyped UMLStandardProfile::Entity

Component 3 is stereotyped with another stereotype that is not registered by default.


# Results before the change

org.eclipse.acceleo.engine.AcceleoEvaluationException: Error while resolving module dependencies.
        at org.eclipse.acceleo.engine.internal.environment.AcceleoEvaluationEnvironment.getAllCandidateNamesakes(AcceleoEvaluationEnvironment.java:630)
        at org.eclipse.acceleo.engine.internal.environment.AcceleoEvaluationEnvironment.getAllCandidates(AcceleoEvaluationEnvironment.java:414)
        at org.eclipse.acceleo.engine.internal.evaluation.AcceleoEvaluationVisitor.prepareInvocation(AcceleoEvaluationVisitor.java:1750)
        at org.eclipse.acceleo.engine.internal.evaluation.AcceleoEvaluationVisitor.visitAcceleoQueryInvocation(AcceleoEvaluationVisitor.java:850)

		

# Results after the change

			0 Component name: Component3
		
			1  	2 Component name (default printName): Component1 (any stereotype)
		
			1  	Component name (overriden default printName): 	
