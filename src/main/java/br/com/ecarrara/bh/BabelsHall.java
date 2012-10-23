package br.com.ecarrara.bh;

import br.com.ecarrara.bh.entities.Message;
import br.com.ecarrara.bh.entities.Person;
import br.com.ecarrara.bh.infra.Display;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.springframework.stereotype.Service;

/**
 *
 * @author ecarrara
 */
@Service
class BabelsHall {

    private final ConcurrentHashMap<String, Person> guests;
    private final Lock lock;
    
    public BabelsHall() {
        this.guests = new ConcurrentHashMap<String, Person>();
        this.lock = new ReentrantLock();
    }
    
    public Message register( String id, String name, Display display ) {
        lock.lock();
        Message message = null;
        try {
            if( !guests.containsKey( id ) )
            {
                Person newGuest = new Person( id, name, display );
                message = new Message( String.format( "%s be welcome!", 
                                                      newGuest.getName() ) );
                for( Person person : guests.values() ) {
                    person.notify( new Message( String.format( 
                                   "\r\n%s entered the Hall! Salute!", 
                                   newGuest.getName() ) ) );
                }
                guests.put( id, newGuest );
            } else {
                message = new Message( "" );
            }
        } finally {
            lock.unlock();
        }
        return message;
    }

    public Message speak( String id, String phrase ) {
        lock.lock();
        Message message = null;
        try {
            if( guests.containsKey( id ) )
            {
                Person currentSpeaker = guests.get( id );
                String nofication = String.format( "\r\n%s: %s", currentSpeaker.getName(), phrase ); 
                for ( Person person : guests.values() ) {
                    person.notify( new Message( nofication ) );
                }
            } else {
                message = new Message( "You need to register first." );
            }
        } finally {
            lock.unlock();
        }
        return message;
    }

    public void bind( String id, Display display ) {
        //need to find a good way to avoid the client to rebind
        //if it was no registered or there is an error
        Person person = guests.get( id );
        if( person != null ) {
            person.setDisplay( display );
        }
    }

    public Message leave( String id ) {
        lock.lock();
        Message message = null;
        try {
            if( guests.containsKey( id ) )
            {
                Person leavingGuest = guests.get( id );
                message = new Message( "\r\nYou left the hall..." );
                guests.remove( id );
                String notification = String.format( "\r\n%s left the hall...", leavingGuest.getName() ); 
                for ( Person person : guests.values() ) {
                    person.notify( new Message( notification ) );
                }
            } else {
                message = new Message( "" );
            }
        } finally {
            lock.unlock();
        }
        return message;
    }
}
