package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.bank_info_recharge;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        /*return ok(index.render("Your new application is ready."));*/

//        String replace="*";
//        String check=",";
//        List<AdminEmergency> lst=AdminEmergency.find.all();
//        for(AdminEmergency u:lst)
//        {
//            if(u.position_start!=null)
//            {
//                if(u.position_start.contains(check))
//                {
//                    u.position_start=u.position_start.replace(check,replace);
//                    u.update();
//                }
//            }
//        }
        return ok(bank_info_recharge.render("Nội dung thông báo"));
    }

}
