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
            gen.writeStringField("type", "session");
            gen.writeStringField("id", ((StartNewSessionEvent) event).getSessionId());
            gen.writeStringField("author", "user");
            gen.writeStringField("time", ((StartNewSessionEvent) event).getTime());
        } else if(event instanceof RegisterEPackageEvent) {
            gen.writeStringField("type", "register");
            gen.writeStringField("epackage-name", ((RegisterEPackageEvent) event).getEPackage().getName());
            gen.writeStringField("epackage-nsURI", ((RegisterEPackageEvent) event).getEPackage().getNsURI());
            gen.writeStringField("epackage-nsPrefix", ((RegisterEPackageEvent) event).getEPackage().getNsPrefix());
        } else if(event instanceof CancelEvent) {
            gen.writeStringField("type", "cancel");
            gen.writeNumberField("offset", ((CancelEvent) event).getLineToCancelOffset());
        } else if(event instanceof CreateEObjectEvent) {
            gen.writeStringField("type", "create");
            gen.writeStringField("id", ((CreateEObjectEvent) event).getId());
            gen.writeStringField("eclass-name", ((CreateEObjectEvent) event).getEClass().getName());
        } else if(event instanceof AddToResourceEvent) {
            gen.writeStringField("type", "add-to-resource");
            gen.writeNumberField("position", event.getPosition());
            gen.writeStringField("value", event.getValue().toString());
        } else if(event instanceof RemoveFromResourceEvent) {
            gen.writeStringField("type", "remove-from-resource");
            gen.writeStringField("value", event.getValue().toString());
        } else if(event instanceof AddToEReferenceEvent) {
            gen.writeStringField("type", "add-to-ereference");
            gen.writeStringField("name", event.getName());
            gen.writeStringField("target", ((AddToEReferenceEvent) event).getTarget().toString());
            gen.writeNumberField("position", event.getPosition());
            gen.writeStringField("value", event.getValue().toString());
        } else if(event instanceof RemoveFromEReferenceEvent) {
            gen.writeStringField("type", "remove-from-ereference");
            gen.writeStringField("name", event.getName());
            gen.writeStringField("target", ((RemoveFromEReferenceEvent) event).getTarget().toString());
            gen.writeStringField("value", event.getValue().toString());
        } else if(event instanceof SetEAttributeEvent) {
            gen.writeStringField("type", "set-eattribute");
            gen.writeStringField("target", ((SetEAttributeEvent) event).getTarget().toString());
            gen.writeStringField("value", event.getName());
        } else if(event instanceof SetEReferenceEvent) {
            gen.writeStringField("type", "set-ereference");
            gen.writeStringField("name", event.getName());
            gen.writeStringField("target", ((SetEReferenceEvent) event).getTarget().toString());
            gen.writeStringField("value", event.getValue().toString());
        } else if(event instanceof UnsetEAttributeEvent) {
            gen.writeStringField("type", "unset-eattribute");
            gen.writeStringField("name", event.getName());
            gen.writeStringField("target", ((UnsetEAttributeEvent) event).getTarget().toString());
        } else if(event instanceof UnsetEReferenceEvent) {
            gen.writeStringField("type", "unset-ereference");
            gen.writeStringField("name", event.getName());
            gen.writeStringField("target", ((UnsetEReferenceEvent) event).getTarget().toString());
        } else if(event instanceof AddToEAttributeEvent) {
            gen.writeStringField("type", "add-to-eattribute");
            gen.writeStringField("name", event.getName());
            gen.writeStringField("target", ((AddToEAttributeEvent) event).getTarget().toString());
        } else if(event instanceof RemoveFromEAttributeEvent) {
            gen.writeStringField("type", "remove-from-eattribute");
            gen.writeStringField("name", event.getName());
            gen.writeStringField("target", ((RemoveFromEAttributeEvent) event).getTarget().toString());
        } else if(event instanceof MoveWithinEReferenceEvent) {
            gen.writeStringField("type", "move-in-eattribute");
            gen.writeStringField("name", event.getName());
            gen.writeStringField("target", ((MoveWithinEReferenceEvent) event).getTarget().toString());
            gen.writeNumberField("from", ((MoveWithinEReferenceEvent) event).getFromPosition());
            gen.writeNumberField("to", ((MoveWithinEReferenceEvent) event).getToPosition());
            gen.writeStringField("value", event.getValue().toString());
        } else if(event instanceof MoveWithinEAttributeEvent) {
            gen.writeStringField("type", "move-in-eattribute");
            gen.writeStringField("name", event.getName());
            gen.writeStringField("target", ((MoveWithinEAttributeEvent) event).getTarget().toString());
            gen.writeNumberField("from", ((MoveWithinEAttributeEvent) event).getFromPosition());
            gen.writeNumberField("to", ((MoveWithinEAttributeEvent) event).getToPosition());
            gen.writeStringField("value", event.getValue().toString());
        } else if(event instanceof DeleteEObjectEvent) {
            gen.writeStringField("type", "delete");
            gen.writeStringField("id", ((DeleteEObjectEvent) event).getId());
            gen.writeStringField("eclass", ((DeleteEObjectEvent) event).getEClass().getName());
        }
        gen.writeEndObject();
    }
}
