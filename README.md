# HopHacksTheSequel

You need to add these two lines to dependencies in gradle module:<br/><br/>

compile 'org.apache.httpcomponents:httpcore:4.4.1'<br/>
compile 'org.apache.httpcomponents:httpclient:4.5'<br/>
<br/><br/>
And add this in the android section of the gradle module<br/><br/>

useLibrary 'org.apache.http.legacy'
