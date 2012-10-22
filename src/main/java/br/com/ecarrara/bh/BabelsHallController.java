package br.com.ecarrara.bh;

import java.io.IOException;
import javax.servlet.AsyncContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ecarrara
 */
@WebServlet( urlPatterns = { "/babelshall" },
             asyncSupported = true, loadOnStartup=1 )
public class BabelsHallController extends HttpServlet {
    
    private BabelsHall babelsHall;
    
    @Override
    public void init( ServletConfig config ) throws ServletException {
        super.init( config );
        babelsHall = new BabelsHall();
    }
    
    @Override
    protected void doGet( HttpServletRequest req,
                          HttpServletResponse resp )
            throws ServletException, IOException {      
        
        String action = req.getParameter( "action" );
        String id = req.getSession(true).getId();
        
        if( "register".equals( action ) ) {
            String name = req.getParameter( "name" );
            AsyncContext ctx = req.startAsync();
            babelsHall.register( id, name, ctx);
        } else if( "speak".equals( action ) ) {
            String phrase = req.getParameter( "phrase" );
            babelsHall.speak( id, phrase );
        } else if("leave".equals( action ) ) {
            babelsHall.leave( id );
        } else if( "bind".equals( action ) ) {
            AsyncContext ctx = req.startAsync();
            babelsHall.bind( id, ctx ); 
        } 
    }
}
