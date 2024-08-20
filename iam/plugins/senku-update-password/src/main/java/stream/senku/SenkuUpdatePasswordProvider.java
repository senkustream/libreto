package stream.senku;

import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticatorUtil;
import org.keycloak.authentication.RequiredActionContext;
import org.keycloak.authentication.requiredactions.UpdatePassword;
import org.keycloak.events.Details;
import org.keycloak.events.Errors;
import org.keycloak.events.EventBuilder;
import org.keycloak.events.EventType;
import org.keycloak.models.ModelException;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.utils.FormMessage;
import org.keycloak.services.messages.Messages;
import org.keycloak.services.validation.Validation;
import org.keycloak.sessions.AuthenticationSessionModel;

import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;

public class SenkuUpdatePasswordProvider extends UpdatePassword
{
    private static final Logger logger = Logger.getLogger(SenkuUpdatePasswordProvider.class);

    @Override
    public void processAction(RequiredActionContext context) {
        EventBuilder event = context.getEvent();
        AuthenticationSessionModel authSession = context.getAuthenticationSession();
        UserModel user = context.getUser();
        MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();
        event.event(EventType.UPDATE_PASSWORD);
        String passwordNew = formData.getFirst("password-new");
        String passwordConfirm = formData.getFirst("password-confirm");

        EventBuilder errorEvent = event.clone().event(EventType.UPDATE_PASSWORD_ERROR)
            .client(authSession.getClient())
            .user(authSession.getAuthenticatedUser());

        if (Validation.isBlank(passwordNew)) {
            Response challenge = context.form()
                .setAttribute("username", authSession.getAuthenticatedUser().getUsername())
                .addError(new FormMessage(Validation.FIELD_PASSWORD_CONFIRM, Messages.UPDATE_PASSWORD))
                .createResponse(UserModel.RequiredAction.UPDATE_PASSWORD);
            context.challenge(challenge);
            errorEvent.error(Errors.PASSWORD_MISSING);
            return;
        } else if (!passwordNew.equals(passwordConfirm)) {
            Response challenge = context.form()
                .setAttribute("username", authSession.getAuthenticatedUser().getUsername())
                .addError(new FormMessage(Validation.FIELD_PASSWORD_CONFIRM, Messages.NOTMATCH_PASSWORD))
                .createResponse(UserModel.RequiredAction.UPDATE_PASSWORD);
            context.challenge(challenge);
            errorEvent.error(Errors.PASSWORD_CONFIRM_ERROR);
        }

        if ("on".equals(formData.getFirst("logout-sessions"))) {
            AuthenticatorUtil.logoutOtherSessions(context);
        }

        try {
            user.credentialManager().updateCredential(UserCredentialModel.password(passwordNew, false));
            logger.info("SENKU intercept: update password user in Radius user account.");
            context.success();
        } catch (ModelException me) {
            errorEvent.detail(Details.REASON, me.getMessage()).error(Errors.PASSWORD_REJECTED);
            Response challenge = context.form()
                .setAttribute("username", authSession.getAuthenticatedUser().getUsername())
                .setError(me.getMessage(), me.getParameters())
                .createResponse(UserModel.RequiredAction.UPDATE_PASSWORD);
            context.challenge(challenge);
            return;
        } catch (Exception ape) {
            errorEvent.detail(Details.REASON, ape.getMessage()).error(Errors.PASSWORD_REJECTED);
            Response challenge = context.form()
                .setAttribute("username", authSession.getAuthenticatedUser().getUsername())
                .setError(ape.getMessage())
                .createResponse(UserModel.RequiredAction.UPDATE_PASSWORD);
            context.challenge(challenge);
            return;
        }
    }
}
