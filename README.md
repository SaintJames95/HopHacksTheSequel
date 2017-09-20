# HopHacksTheSequel

You need to add these two lines to dependencies in gradle module:

compile 'org.apache.httpcomponents:httpcore:4.4.1'
compile 'org.apache.httpcomponents:httpclient:4.5'

And add this in the android section of the gradle module

useLibrary 'org.apache.http.legacy'
