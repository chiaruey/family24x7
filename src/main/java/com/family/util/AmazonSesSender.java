package com.family.util;

import com.amazonaws.services.simpleemail.*;
import com.amazonaws.services.simpleemail.model.*;
import com.amazonaws.regions.*;

public class AmazonSesSender {

	// Supply your SMTP credentials below. Note that your SMTP credentials are
	// different from your AWS credentials.
	static final String SMTP_USERNAME = ""; 
	static final String SMTP_PASSWORD = ""; 
	static final String HOST = "email-smtp.us-east-1.amazonaws.com";

	// Port we will connect to on the Amazon SES SMTP endpoint. We are choosing
	// port 25 because we will use
	// STARTTLS to encrypt the connection.
	static final int PORT = 25;

	public static void main(String[] args) throws Exception {
		String toAddress = "chiarueylu@gmail.com";
		String fromAddress = "family24x7@gmail.com";
		String emailSubject = "Your temporary password for family24x7 is here";
		String bodyText = "dear chia-ruey \n\n your temporary password is 12xx343 \n\n Thanks\n\nFamily24x7 support team";
		sendMail(toAddress, fromAddress, emailSubject, bodyText);
	}

	public static void sendMail(String toAddress, String fromAddress,
			String emailSubject, String bodyText) throws Exception {

	       // Construct an object to contain the recipient address.
        Destination destination = new Destination().withToAddresses(new String[]{toAddress});
        
        // Create the subject and body of the message.
        Content subject = new Content().withData(emailSubject);
        Content textBody = new Content().withData(bodyText); 
        Body body = new Body().withText(textBody);
        
        // Create a message with the specified subject and body.
        Message message = new Message().withSubject(subject).withBody(body);
        
        // Assemble the email.
        SendEmailRequest request = new SendEmailRequest().withSource(fromAddress).withDestination(destination).withMessage(message);
        
        try
        {        
            System.out.println("Attempting to send an email through Amazon SES by using the AWS SDK for Java...");
        
            // Instantiate an Amazon SES client, which will make the service call. The service call requires your AWS credentials. 
            // Because we're not providing an argument when instantiating the client, the SDK will attempt to find your AWS credentials 
            // using the default credential provider chain. The first place the chain looks for the credentials is in environment variables 
            // AWS_ACCESS_KEY_ID and AWS_SECRET_KEY. 
            // For more information, see http://docs.aws.amazon.com/AWSSdkDocsJava/latest/DeveloperGuide/credentials.html
            AmazonSimpleEmailServiceClient client = new AmazonSimpleEmailServiceClient();
               
            // Choose the AWS region of the Amazon SES endpoint you want to connect to. Note that your sandbox 
            // status, sending limits, and Amazon SES identity-related settings are specific to a given AWS 
            // region, so be sure to select an AWS region in which you set up Amazon SES. Here, we are using 
            // the US West (Oregon) region. Examples of other regions that Amazon SES supports are US_EAST_1 
            // and EU_WEST_1. For a complete list, see http://docs.aws.amazon.com/ses/latest/DeveloperGuide/regions.html 
            Region REGION = Region.getRegion(Regions.US_WEST_2);
            client.setRegion(REGION);
       
            // Send the email.
            client.sendEmail(request);  
            System.out.println("Email sent!");
        }
        catch (Exception ex) 
        {
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
        }
	}

}
