export class DOMOutsideEventPlugin {

  documentEventMap = {
    "clickout": "click",
    "mousedownout": "mousedown",
    "mouseupout": "mouseup",
    "mousemoveout": "mousemove"
  };

  addEventListener(element, eventName, handler) {
    // NOTE: The "manager" is injected by the Angular framework (via
    // the Event Manager that aggregates the event plugins).
    var zone = (<any>this).manager.getZone();
    // Each "outside" event is captured by an "inside" event at the
    // document level. Translate the element-local event type to the
    // document-local event type.
    var documentEvent = this.documentEventMap[eventName];
    // Zone.js patches event-target code. As such, when we attach the
    // the document-level event handler, we want to do so outside of
    // the change-detection zone so that our checkEventTarget()
    // doesn't trigger more change-detection than it has to. Once we
    // know that we have to parle the document-level event into an
    // element-local event, we'll re-enter the Angular zone.
    zone.runOutsideAngular(addDocumentEventListener);
    return ( removeDocumentEventListener );
    // I attach the document-local event listener which will determine
    // the origin of the bubbled-up events.
    function addDocumentEventListener() {
      document.addEventListener(documentEvent, checkEventTarget, true);
    }

    // I detach the document-local event listener, tearing down the
    // "outside" event binding.
    function removeDocumentEventListener() {
      document.removeEventListener(documentEvent, checkEventTarget, true);
    }

    // I check to see if the given event originated from within the
    // host element. If it did, the event is ignored. If it did NOT,
    // then the "outside" event binding is invoked with the given event.
    function checkEventTarget(event) {
      var current = event.target;
      do {
        if (current === element) {
          return;
        }
      } while (current.parentNode && ( current = current.parentNode ));
      // If we made it this far, we didn't bubble past the host
      // element. As such, we know that the event was initiated
      // from outside the host element. It is therefore an
      // "outside" event and needs to be translated into a host-
      // local event that integrates with change-detection.
      triggerDOMEventInZone(event);
    }

    // I invoke the host event handler with the given event.
    function triggerDOMEventInZone(event) {
      // Now that we know that the document-local event has to be
      // translated into an element-local host binding event, we
      // need to re-enter the Angular 2 change-detection zone so
      // that view-model changes made within the event handler will
      // trigger a new round of change-detection.
      zone.run(
        function runInZone() {
          handler(event);
        }
      );
    };
  } // END: addEventListener().
  // I register the event on the global target and return the event
  // de-registration method.
  addGlobalEventListener(target, eventName, handler) {
    // For the purposes of an "outside" event, it will never be
    // possible to actually click / mouse outside of the document
    // or the window object. As such, simply ignore these global
    // context, providing a no-op binding.
    if (( target === "document" ) || ( target === "window" )) {
      return ( this.noop );
    }
    // If the target was not "document" or "window", it must be body
    // (the only other "global" host binding). While not very likely,
    // it is possible to click outside of the body tag (by clicking
    // on the HTML tag). As such, let's add the event listener to the
    // body tag directly.
    return ( this.addEventListener(document.body, eventName, handler) );
  }

  // I check to see if the given event is supported by the plugin.
  supports(eventName) {
    // If the event can be mapped to a native event on the document,
    // then we can support the event.
    return ( this.documentEventMap.hasOwnProperty(eventName) );
  }

  // ---
  // PRIVATE METHODS.
  // ---
  // I perform a no-operation instruction.
  private noop() {
    // Nothing to see here, folks.
  }
}
