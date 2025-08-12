package metamodel.src.cbp;

import org.eclipse.emf.ecore.EObject;

public interface MoveInEAttribute extends EObject {
	String getFrom();
	void setFrom(String from);

	String getTo();
	String setTo(String to);

	String getName();
	void setName(String getName);

	String getTarget();
	void setTarget(String target);

	Value getValue();
	void setValue(Value value);
}