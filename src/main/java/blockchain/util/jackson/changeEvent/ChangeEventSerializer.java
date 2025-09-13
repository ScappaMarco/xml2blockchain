package blockchain.util.jackson;

import cbp.src.event.*;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class ChangeEventSerializer extends JsonSerializer<ChangeEvent> {

    //Jackson serializer using instanceof (? extends ChangeEvent)
    @Override
    public void serialize(ChangeEvent event, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        if(event instanceof StartNewSessionEvent) {
            //System.out.println("serializing a session: " + ((StartNewSessionEvent) event).getSessionId());
            gen.writeStringField("type", "session");
            gen.writeStringField("session-id", ((StartNewSessionEvent) event).getSessionId());
            gen.writeStringField("author", "user");
            gen.writeStringField("timestamp", ((StartNewSessionEvent) event).getTime());
        } else if(event instanceof RegisterEPackageEvent) {
            //System.out.println("serializing a register Epackage");
            gen.writeStringField("type", "register");
            gen.writeStringField("epackage-name", ((RegisterEPackageEvent) event).getEPackage().getName());
            gen.writeStringField("epackage-nsURI", ((RegisterEPackageEvent) event).getEPackage().getNsURI());
            gen.writeStringField("epackage-nsPrefix", ((RegisterEPackageEvent) event).getEPackage().getNsPrefix());
        } else if(event instanceof CancelEvent) {
            //System.out.println("serializing a Cancel event");
            gen.writeStringField("type", "cancel");
            gen.writeNumberField("offset", ((CancelEvent) event).getLineToCancelOffset());
        } else if(event instanceof CreateEObjectEvent) {
            //System.out.println("serializing a Create");
            gen.writeStringField("type", "create");
            gen.writeStringField("eobject-id", ((CreateEObjectEvent) event).getId());
            gen.writeStringField("eclass-name", ((CreateEObjectEvent) event).getEClass().getName());
        } else if(event instanceof AddToResourceEvent) {
            //System.out.println("serializing a Add To Resource");
            gen.writeStringField("type", "add-to-resource");
            gen.writeNumberField("position", event.getPosition());
            gen.writeStringField("value", event.getValueId());
        } else if(event instanceof RemoveFromResourceEvent) {
            //System.out.println("serializing a Remove from Resource");
            gen.writeStringField("type", "remove-from-resource");
            gen.writeStringField("value", event.getValueId());
        } else if(event instanceof AddToEReferenceEvent) {
            //System.out.println("serializing a Add to Reference");
            gen.writeStringField("type", "add-to-ereference");
            gen.writeStringField("name", event.getName());
            gen.writeStringField("target-class", ((AddToEReferenceEvent) event).getTarget().eClass().getName());
            gen.writeStringField("target-id", ((AddToEReferenceEvent) event).getTargetId());
            gen.writeNumberField("position", event.getPosition());
            gen.writeStringField("value", event.getValueId());
        } else if(event instanceof RemoveFromEReferenceEvent) {
            //System.out.println("serializing a Remove from Reference");
            gen.writeStringField("type", "remove-from-ereference");
            gen.writeStringField("name", event.getName());
            gen.writeStringField("target-class", ((RemoveFromEReferenceEvent) event).getTarget().eClass().getName());
            gen.writeStringField("target-id", ((RemoveFromEReferenceEvent) event).getTargetId());
            gen.writeStringField("value", event.getValueId());
        } else if(event instanceof SetEAttributeEvent) {
            //System.out.println("serializing a Set attribute");
            gen.writeStringField("type", "set-eattribute");
            gen.writeStringField("target-class", ((SetEAttributeEvent) event).getTarget().eClass().getName());
            gen.writeStringField("target-id", ((SetEAttributeEvent) event).getTargetId());
            gen.writeStringField("value", event.getValueId());
        } else if(event instanceof SetEReferenceEvent) {
            //System.out.println("serializing a Set Reference");
            gen.writeStringField("type", "set-ereference");
            gen.writeStringField("name", event.getName());
            gen.writeStringField("target-class", ((SetEReferenceEvent) event).getTarget().eClass().getName());
            gen.writeStringField("target-id", ((SetEReferenceEvent) event).getTargetId());
            gen.writeStringField("value", event.getValueId());
        } else if(event instanceof UnsetEAttributeEvent) {
            //System.out.println("serializing a Unset attribute");
            gen.writeStringField("type", "unset-eattribute");
            gen.writeStringField("name", event.getName());
            gen.writeStringField("target-class", ((UnsetEAttributeEvent) event).getTarget().eClass().getName());
            gen.writeStringField("target-id", ((UnsetEAttributeEvent) event).getTargetId());
        } else if(event instanceof UnsetEReferenceEvent) {
            //System.out.println("serializing a Unset Reference");
            gen.writeStringField("type", "unset-ereference");
            gen.writeStringField("name", event.getName());
            gen.writeStringField("target-class", ((UnsetEReferenceEvent) event).getTarget().eClass().getName());
            gen.writeStringField("target-id", ((UnsetEReferenceEvent) event).getTargetId());
        } else if(event instanceof AddToEAttributeEvent) {
            //System.out.println("serializing a Add to Attribute");
            gen.writeStringField("type", "add-to-eattribute");
            gen.writeStringField("name", event.getName());
            gen.writeStringField("target-class", ((AddToEAttributeEvent) event).getTarget().eClass().getName());
            gen.writeStringField("target-id", ((AddToEAttributeEvent) event).getTargetId());
            gen.writeStringField("value", event.getValueId());
        } else if(event instanceof RemoveFromEAttributeEvent) {
            //System.out.println("serializing a Remove from Attribute");
            gen.writeStringField("type", "remove-from-eattribute");
            gen.writeStringField("name", event.getName());
            gen.writeStringField("target-class", ((RemoveFromEAttributeEvent) event).getTarget().eClass().getName());
            gen.writeStringField("target-id", ((RemoveFromEAttributeEvent) event).getTargetId());
            gen.writeStringField("value", event.getValueId());
        } else if(event instanceof MoveWithinEReferenceEvent) {
            //System.out.println("serializing a Move in Reference");
            gen.writeStringField("type", "move-in-ereference");
            gen.writeStringField("name", event.getName());
            gen.writeStringField("target-class", ((MoveWithinEReferenceEvent) event).getTarget().eClass().getName());
            gen.writeStringField("target-id", ((MoveWithinEReferenceEvent) event).getTargetId());
            gen.writeNumberField("from", ((MoveWithinEReferenceEvent) event).getFromPosition());
            gen.writeNumberField("to", ((MoveWithinEReferenceEvent) event).getToPosition());
            gen.writeStringField("value", event.getValueId());
        } else if(event instanceof MoveWithinEAttributeEvent) {
            //System.out.println("serializing a Move in Attribute");
            gen.writeStringField("type", "move-in-eattribute");
            gen.writeStringField("name", event.getName());
            gen.writeStringField("target-class", ((MoveWithinEAttributeEvent) event).getTarget().eClass().getName());
            gen.writeStringField("target-id", ((MoveWithinEAttributeEvent) event).getTargetId());
            gen.writeNumberField("from", ((MoveWithinEAttributeEvent) event).getFromPosition());
            gen.writeNumberField("to", ((MoveWithinEAttributeEvent) event).getToPosition());
            gen.writeStringField("value", event.getValueId());
        } else if(event instanceof DeleteEObjectEvent) {
            //System.out.println("serializing a Delete");
            gen.writeStringField("type", "delete");
            gen.writeStringField("delete-id", ((DeleteEObjectEvent) event).getId());
            gen.writeStringField("eclass", ((DeleteEObjectEvent) event).getEClass().getName());
            //NOTE: the eClass of the delete will be the eClass of the corresponding EObject (by the id attribute)
        }
        gen.writeEndObject();
    }
}