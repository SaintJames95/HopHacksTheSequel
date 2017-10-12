# The Snap Responders

This is a work in progress<br/>

You need to add these two lines to dependencies in gradle module:<br/><br/>

compile 'org.apache.httpcomponents:httpcore:4.4.1'<br/>
compile 'org.apache.httpcomponents:httpclient:4.5'<br/>
<br/><br/>
And add this in the android section of the gradle module<br/><br/>

useLibrary 'org.apache.http.legacy'

This is an Android Application that allows people in times of crisis to quickly send a photo and GPS location to registered responders. The app maps out the location of where the photos were taken and the associated images, allowing responders to more easily prioritize locations to head out to. Those who send the images and location will be notified when someone is on the way. 
