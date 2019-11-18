package org.eclipse.acceleo.module.dynamic_override_main.service;

/*-
 * #%L
 * org.eclipse.acceleo.module.samsarasoftware
 * %%
 * Copyright (C) 2014 - 2017 Pere Joseph Rodriguez
 * %%
 * This software is property of Pere Joseph Rodriguez (Spain) and is distributed under licensing.
You must not have acces to this file unless with its propietary permission.
You may obtain a copy of the License at

	http://www.samsara-software.es/licenses/license.txt
	
See the License for the specific language governing permissions and
limitations under the License.
 * #L%
 */

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ocl.ecore.TypeType;
import org.eclipse.ocl.ecore.internal.OCLFactoryImpl;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.UMLPlugin;

public class MetaClassService {

	public EObject getStereotypeMetaClass(Stereotype stereotype){
//		stereotype.getProfile().getURI();
//		stereotype.getProfile().getDefinition().getNsPrefix();
//		UMLPlugin.getEPackageNsURIToProfileLocationMap();
		
			EPackage a =stereotype.getProfile().getDefinition();
			return a.getEFactoryInstance().create((EClass) a.getEClassifier(stereotype.getName()));
		//stereotype.getClass().getProtectionDomain().getCodeSource();
//		UMLPlugin.getEPackageNsURIToProfileLocationMap().get(((Profile)stereotype.getNamespace()).getURI().toString());
		 
	}
	
	public TypeType getMetaClassOclType(EClass metaclass){
		return org.eclipse.ocl.ecore.impl.TypeTypeImpl.createTypeType(metaclass);
	}
}
