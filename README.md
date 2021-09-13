# RemoteAppUpdater
Remote App Updater Library can be used for updating the application from any Url. It download and save it to External/Internal Storage based on the path provided and then automatic installing the apk. Please add provider.xml and android runtime permission before using this library. See sample application use in app folder.

[![](https://jitpack.io/v/TripathiViky/RemoteUpdateApp.svg)](https://jitpack.io/#TripathiViky/RemoteUpdateApp)

## Usage 

#### Step 1. Add the JitPack repository to your build file 

Add it in your root build.gradle at the end of repositories: </br> 


	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
#### Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.TripathiViky:RemoteUpdateApp:1.0'
	}
  
  
#### Step 3. Add Runtime Permission 

Remember to add below  permission in `Manifest.xml file` . And Also add runtime permission for (Version => Marshmallow ). See the sample app. 

     <uses-permission android:name="android.permission.INTERNET" />
  
     <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
     
     <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" /> 
     
 
 ####  Step 4. Add Provider XML 
 Create a folder called `xml` in `res` folder. Create a xml file and named it `provider_paths`. Paste below code: 
 
    <?xml version="1.0" encoding="utf-8"?>
    <paths xmlns:android="http://schemas.android.com/apk/res/android">
    	<external-path name="external_files" path="."/>
    </paths>
      
 Add below code in your `AndroidManifest.xml` file.    
 

    <application
      
	..............
        <provider
            android:name="androidx.core.content.FileProvider"
            android:authorities="${applicationId}.provider"
            android:exported="false"
            android:grantUriPermissions="true">
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/provider_paths"/>
        </provider>


    </application>

 
 #### Step 5. For downloading and installing the apk automatically.
 
 After setting up all neccessary files it's just two line of code to download and install the apk updates in your device. 
  	
	String url = "https://github.com/TripathiViky/AndroidAppDeploymentTest/blob/main/app-debug.apk?raw=true";
	
	AppUpdater app = new AppUpdater(MainActivity.this);
       	
	// With custom fileName, e.g. 'Update 2.0'
 	app.updateAppWithUrl(url, "yourFileName");
 
 
 You are Good To Go. Happy Coding
 
 ### MIT License

#### Copyright (c) 2021 Vikas Tripathi

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
