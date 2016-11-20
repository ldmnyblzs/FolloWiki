package beans;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import entities.User;
import logic.Constant;


public class AuthenticationPhaseListener implements PhaseListener {
        
    // --- IMPLEMENTALT METODUSOK --- //
    
    public void afterPhase(PhaseEvent event) {
        FacesContext fcontext = event.getFacesContext();
        ExternalContext context = event.getFacesContext().getExternalContext();
        String path = context.getRequestPathInfo();
        User user = (User) context.getSessionMap().get(Constant.SESSION_KEY);
        
        // Ha publikus oldal: mindig tovabb mehet!        
        if (path.equals(Constant.INDEX_SIDE_PATH)
                || path.equals(Constant.LOGIN_SIDE_PATH)
                || path.equals(Constant.SIGN_UP_SIDE_PATH)
                || path.equals(Constant.NO_PERMISSION_SIDE_PATH)) {
            return;
        }
        // Ha bejelentkezett:
        // - User: foglalo oldal megengedve
        // - Admin: minden oldal megengedve
        if (context.getSessionMap().containsKey(Constant.SESSION_KEY)) {
            
            if (user.getRole().equals(Constant.USER_ROLE)
                    && (path.equals(Constant.CONTROL_SIDE_PATH)
                    		|| (path.equals(Constant.SUB_SIDE_PATH)))) {
                return;
            }
            else if (user.getRole().equals(Constant.ADMIN_ROLE)) {
                return;
            }
            else {
                try {
                    fcontext.responseComplete();
                    context.redirect(Constant.NO_PERMISSION_SIDE_PATH);
                } catch (IOException ex) {
                    Logger.getLogger(AuthenticationPhaseListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        // Ha nincs bejelentkezve: jelentkezzen be
        else {
            fcontext.responseComplete();              
            fcontext.getApplication().getNavigationHandler()
                    .handleNavigation(fcontext, null, Constant.SIGN_IN_KEY);
        }
    }
    
    public void beforePhase(PhaseEvent event) {        
    }
    
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
}