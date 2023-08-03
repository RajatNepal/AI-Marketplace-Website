package com.cravin.marketplace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

/**
 * Contains all methods required to initialize data in the databases
 */

@Controller
public class initDatabase {
     @Autowired
     UserRepository userRepository;

     @Autowired
     ModelRepository modelRepository;

    /**
     * Method is called when the website is launched via the 'Event Listener'
     * annotation.
     * This method initializes 10 models into the database and sets up admin
     * account.
     */
     @EventListener(ApplicationReadyEvent.class)
     public void doSomethingAfterStartup() {
         AiModel aimodel1 = new AiModel("IoT Bridge for SiteWisen", false, 9855.00, "IoT Bridge is an AMI product.", "/IoTBridge.png", true);
         modelRepository.save(aimodel1);
 
         AiModel aimodel2 = new AiModel("Red Hat Ansible Automation Platform 2", false, 5833.33,
                 "This offering is covered by the Premium tier of Red Hat Support.","/RedHat.png", true);
         modelRepository.save(aimodel2);
 
         AiModel aimodel3 = new AiModel("KNIME Server Medium for AWS", false, 33350.00, "KNIME Software for AWS provides a best-in-class solution for building and governing end to end data analytics and machine learning pipelines.",
                 "/Knime.png", true);
         modelRepository.save(aimodel3);
 
         AiModel aimodel4 = new AiModel("Deep Learning AMI DLAMI Ubuntu 18", false, 5999.00,
                 "Deep Learning AMI (DLAMI) Ubuntu 18 is perfect for creating your deep learning models.", "/DLAMI.png", true);
         modelRepository.save(aimodel4);
 
         AiModel aimodel5 = new AiModel("ML Workbench for TensorFlow", false, 200.00, "The Machine Learning Workbench puts a focus on security.",
                 "/MLWork.png", true);
         modelRepository.save(aimodel5);
 
         AiModel aimodel6 = new AiModel("Orthanc DICOM Server with AWS-S3 support", false, 386.00,
                 "Orthanc is a lightweight DICOM server for healthcare and medical research.", "/Orthanc.png", true);
         modelRepository.save(aimodel6);
 
         AiModel aimodel7 = new AiModel("Techlatest Stable Diffusion with AUTOMATIC1111 Web Interface", false, 648.00, "This VM provides a AUTOMATIC1111 web based Stable Diffusion environment which is AI deep learning, text-to-image model.",
                 "/Stable.png", true);
         modelRepository.save(aimodel7);
 
         AiModel aimodel8 = new AiModel("Anaplan", false, 750000.00,
                 "Connected Planning enables dynamic, collaborative, and intelligent planning across all areas of an organization.", "/Anaplan.png", true);
         modelRepository.save(aimodel8);
 
         AiModel aimodel9 = new AiModel("Cloud IDE for Python", false, 999.00, "This VM provides IDE environment for Python using Jupyter Notebook and Visual Studio Code for cloud based and local development environment.",
                 "/Cloud.png", true);
         modelRepository.save(aimodel9);
 
         AiModel aimodel10 = new AiModel("Nerdly", false, 14904.00,
                 "The World's First AWS Software Defined Staffing Company Powered By Machine Learning", "/Nerdly.png", true);
         modelRepository.save(aimodel10);
 
         User admin = createAdminAccount();
         userRepository.save(admin);
     }

    /**
     * Helper method to create the admin User object
     * @return User with admin privlages
     */
     public User createAdminAccount() {
        User u = new User();
        u.setId(0);
        u.setUsername("admin");
        u.setPassword("adminpass");
        u.setIsAdmin(true);
        return u;
    }
 
    
}
