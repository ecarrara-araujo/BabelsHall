package br.com.ecarrara.bh.infra;

import br.com.ecarrara.bh.entities.Message;
import org.springframework.web.context.request.async.DeferredResult;

/**
 *
 * @author ecarrara
 */
public class SpringWebDisplay implements Display {
    
    private DeferredResult<Message> deferredResult;

    public SpringWebDisplay( DeferredResult<Message> deferredResult ) {
        this.deferredResult = deferredResult;
    }

    @Override
    public void show( Message message ) {
        this.deferredResult.setResult( message );
    }
    
}
