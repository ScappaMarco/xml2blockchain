package metamodel.src.cbp;

import org.eclipse.emf.ecore.EObject;

public interface Value extends EObject {
	String getLiteral();
	void setLiteral(String literal);

	String getEobject();
	void setEobject(String eobject);
}