#ImageS3

ImageS3 is a free and open image hosting service for developers. It is designed to store, resize and manage images for all your web and mobile apps in one place.

##Features

 * Create image plants to organize and store images on Amazon S3.
 * Design templates for resizing images into pixels or percents on demand.
 * REST APIs for easy application integration.
 * Support PNG, JPG and BMP formats.
 * Web-based admin tool with live monitoring:
   
   [<img src="http://i.imgur.com/liGhBmNl.png" height="226" width="610" />](http://i.imgur.com/liGhBmN.png)


##Installation

###Installing Requirements

 * **Java** - ImageS3 developed in Java. You can download the latest JRE7 build from [here](http://www.oracle.com/technetwork/java/javase/downloads/jre7-downloads-1880261.html).

 * **Play Framework** - Play Framework is a lightweight and highly-scalable application server. ImageS3 released with Play as a standalone package which lets you don't need to download different jar files separately. Follow this [instruction](https://www.playframework.com/documentation/2.3.x/Installing) to install 2.3.x Play Framework.
 
 * **MongoDB** - MongoDB is a leading NoSQL database with amazing [features](http://www.mongodb.org/). ImageS3 uses MongoDB to store image metadata and other objects information. Follow this [instruction](http://docs.mongodb.org/manual/installation/) to install the latest MongoDB.
 
 * **Amazon S3** -- Amazon S3 is a secure, durable and highly-scalable cloud storage service. ImageS3 uses Amazon S3 to store image files. If you already have Amazon AWS account, you can skip this, otherwise, sign up one at [here](http://aws.amazon.com/s3/).

###Installing ImageS3

Download and unzip the compressed package:

    $ wget https://github.com/gogoup/images3-dist/raw/master/images3-play-latest.zip
    $ unzip images3-play-latest.zip

##Configuring ImageS3

All the configuration files are located in image-play-[version]/conf.

You need to set the following properties before running ImageS3:

 * **imagecontent.properties**
   - *'imagecontent.download.dir'* -- A place used to store images uploaded from clients or download from Amazon S3. Make sure the directory exists.
   
 * **imageprocessor.properties**
   - *'image.processing.tempdir'* -- A place used to store resized images. Make sure the directory exists.

 * **mongodb.properties**
   - *'mongodb.url'* -- MongoDB server IP address.
   - *'mongodb.port'* -- MongoDB server port number. 27017 is the default number.
   - *'mongodb.username'* -- Username of MongoDB connection. Leave this value empty, if you don't setup username on MongoDB.
   - *'mongodb.password'* -- Password of MongoDB connection. Leave this value empty, if you don't setup password on MongoDB.

   
##Running ImageS3

Enable execution persmissions:

    $ chmod +x images3-play-[version]/bin/*

Use the following command to run ImageS3:
    
    $ ./images3-play-[version]/bin/images3-play
    
And then, open [http://localhost:9000](http://localhost:9000) in your browser, you will see the admin tool:

#[<img src="http://i.imgur.com/RcY9QQal.png" width="610" />](http://i.imgur.com/RcY9QQa.png)

You can also run ImageS3 on different port, for example 8080:

    $ ./images3-play-[version]/bin/images3-play -Dhttp.port=8080


##How to Use

Checking the **[wiki](https://github.com/images3/images3-play/wiki)** page.

##License
Released under the Apache License 2.0