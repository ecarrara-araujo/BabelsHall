package br.com.ecarrara.bh;

import br.com.ecarrara.bh.entities.Message;
import br.com.ecarrara.bh.infra.Display;
import br.com.ecarrara.bh.infra.SpringWebDisplay;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

/**
 *
 * @author ecarrara
 */
@Controller
@RequestMapping( value = "/babelshall/*", produces = { "application/json" })
public class BabelsHallController {
    
    @Autowired
    private BabelsHall babelsHall;
    
    @RequestMapping( value = "/register", method = RequestMethod.GET )
    public @ResponseBody Message register( @RequestParam String name, HttpSession session) {
        DeferredResult<Message> deferredResult = new DeferredResult<Message>();
        Display display = new SpringWebDisplay( deferredResult );
        return babelsHall.register( session.getId(), name, display );
    }

    @RequestMapping( value = "/leave", method = RequestMethod.GET )
    public @ResponseBody Message leave( HttpSession session ) {
        return babelsHall.leave( session.getId() );
    }
            
    @RequestMapping( value = "/speak", method = RequestMethod.GET )
    public @ResponseBody Message speak( @RequestParam String phrase, HttpSession session ) {
        return babelsHall.speak( session.getId(), phrase );        
    }

    @RequestMapping( value = "/bind", method = RequestMethod.GET )
    public @ResponseBody DeferredResult<Message> bind( HttpSession session ) {
        DeferredResult<Message> deferredResult = new DeferredResult<Message>();
        Display display = new SpringWebDisplay( deferredResult );        
        babelsHall.bind( session.getId(), display );
        return deferredResult;
    }
}
