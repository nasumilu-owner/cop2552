/**
 * Copyright 2021 Michael Lucas
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.io.IOException;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {
	
	
    private static Scene scene;
    private static Stage mainStage;
    
    private static AccountDao dao;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"), 640, 480);
        mainStage = stage;
        stage.setScene(scene);
        stage.show();
    }
    
    static public void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML("login"));
    }

    static public void setRoot(String fxml, Accounts account) throws IOException {
        scene.setRoot(loadFXML(fxml, account));
    }
    
    /**
     * Lazy load singleton for the applications AccountDao
     * @return
     * @throws AccountException
     */
    static AccountDao getDao() throws AccountException {
    	// this isn't very dynamic should be some service loader
    	// to get the needed DAO
    	if(null == dao) {
    		Optional<AccountDao> ad = AccountDao.prompt(mainStage);
    		ad.orElseThrow(AccountException::createInvalidAccountFile);
    		dao = ad.get();
    	}
    	return dao;
    }

    /**
     * Loads an fmxl
     * @param fxml
     * @return
     * @throws IOException
     */
    private static Parent loadFXML(String fxml) throws IOException {
    	ResourceBundle bundle = ResourceBundle.getBundle(fxml);
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"), bundle);
        return fxmlLoader.load();
    }
    
    /**
     * Loads an fxml to an AccountReciever
     * @param fxml
     * @param account
     * @return
     * @throws IOException
     */
    private static Parent loadFXML(String fxml, Accounts account) throws IOException {
    	ResourceBundle bundle = ResourceBundle.getBundle(fxml);
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"), bundle);
        Parent p = fxmlLoader.load();
        ((AccountReceiver)fxmlLoader.getController()).setAccount(account);
        return p;
    }

    /**
     * 
     * @param args
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws ClassNotFoundException {
        launch();
    }
}