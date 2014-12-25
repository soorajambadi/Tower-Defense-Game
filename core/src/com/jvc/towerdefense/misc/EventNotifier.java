package com.jvc.towerdefense.misc;

public class EventNotifier
{
    private EventHandler eH;
    private boolean somethingHappened; 
    public EventNotifier (EventHandler event)
    {
	    // Save the event object for later use.
    	eH = event; 
	    // Nothing to report yet.
	    somethingHappened = false;
    } 
    //...  
    public void doWork ()
    {
	    // Check the predicate, which is set elsewhere.
	    if (somethingHappened)
	        {
	        // Signal the event by invoking the interface's method.
	        //eH.HandleEvent();
	        }
	    //...
	    } 
	    // ...
}
