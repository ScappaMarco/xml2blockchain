package metamodel.src.cbp;

import org.eclipse.emf.ecore.EObject;

public interface RemoveFromResource extends EObject {
	Value getValue();
	void setValue(Value value);
}