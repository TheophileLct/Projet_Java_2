package contact_app.view;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.time.LocalDate;
import java.util.Optional;

import contact_app.model.Address;
import contact_app.model.Contact;
import contact_app.service.StageService;
import contact_app.service.ViewService;
import contact_app.service.ContactService;
import javafx.scene.Node;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.DirectoryChooser;

public class MainLayoutController {
	
	

	/**
	 * Méthode qui va enclencher l'exportation des données contacts dans le dossier souhaité. 
	 */
	public void exportData()
    {
        System.out.println("exportation des données");
        File directory = dataFolder("export");
        if (directory != null) {
            File newDirectory = new File(directory, LocalDate.now().toString());
            int numberInstanceDirectory = 2;
            while (newDirectory.exists()) {
                newDirectory = new File(directory, LocalDate.now().toString() + " " + numberInstanceDirectory);
                numberInstanceDirectory++;
            }
            newDirectory.mkdirs();
            for(Contact contact : ContactService.getContacts())
            {
                contact.export(newDirectory);
            }
        }
    }
	
	/**
	 * Méthode qui va enclencher l'importation des données contacts du dossier souhaité. 
	 */
	public void importData() throws IOException{
        System.out.println("importation des données");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(StageService.getPrimaryStage());
        alert.setTitle("INFORMATION");
        alert.setHeaderText("Don't forget to make a backup/export of the current database," + "\n" + "Make an import will permanently replace it." + "\n" + "Continue?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) {
            File directory = dataFolder("import");
            if (directory != null) {
                File[] files = directory.listFiles();
                ContactService.clearContacts();
                for (int i = 0; i < files.length; i++) {
                    if (files[i].getAbsolutePath().endsWith("vcf")) {
                       ContactService.addContact(Contact.importFile(files[i]));
                    }
                }
            }
        }
    }
	
	/**
	 * Methode permettant de retourner au home screen
	 */
	public void gotoHome() {
		Node root = ViewService.getView("HomeScreen");
		StageService.showView(root);
	}

	/**
	 * Methode permettant de retourner à la vue de l'agenda de Contacts.
	 */
	public void gotoContactOverview() {
		StageService.showView((Node)ViewService.getView("ContactOverview"));
	}
	
	/**
	 * Methode permettant de quitter l'application.
	 */
	public void gotoCloseApplication() {
		StageService.closeStage();
	}
	
	/**
	 * Methode permettant d'aller à la page About.
	 */
	public void gotoAboutPage() {
		StageService.showView((Node)ViewService.getView("About"));
	}
	
	/**
	 * @param action
	 * @return directory
	 */
	public File dataFolder(String action) {
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select the contact's folder to " + action);
        File directory = null;
        try{
            try{
                directory = directoryChooser.showDialog(StageService.getPrimaryStage());
            }catch(IllegalArgumentException e){
                directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
                directory = directoryChooser.showDialog(StageService.getPrimaryStage());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return directory;
    }
}
