package metamodel.src.cbp;

import org.eclipse.emf.ecore.EObject;

public interface AddToResource extends EObject {
	String getPosition();
	void setPosition(String position);

	Value getValue();
	void setValue(Value value);
}