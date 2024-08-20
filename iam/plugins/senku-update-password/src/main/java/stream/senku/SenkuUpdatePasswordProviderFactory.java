package stream.senku;

import org.keycloak.Config.Scope;
import org.keycloak.authentication.RequiredActionFactory;
import org.keycloak.authentication.RequiredActionProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class SenkuUpdatePasswordProviderFactory implements RequiredActionFactory {

    public static final String ID = "senku-update-password";

    @Override
    public RequiredActionProvider create(KeycloakSession session) {
        return new SenkuUpdatePasswordProvider();
    }

    @Override
    public void init(Scope config) {

    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {

    }

    @Override
    public void close() {
        
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getDisplayText() {
        return "Senku Update Password";
    }
    
}
