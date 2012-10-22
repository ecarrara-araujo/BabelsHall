package br.com.ecarrara.bh;

import br.com.ecarrara.bh.entities.Message;
import br.com.ecarrara.bh.entities.Person;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.servlet.AsyncContext;

/**
 *
 * @author ecarrara
 */
class BabelsHall {

    private final ConcurrentHashMap<String, Person> guests;
    private final Lock lock;
    
    public BabelsHall() {
        this.guests = new ConcurrentHashMap<String, Person>();
        this.lock = new ReentrantLock();
    }
    
    void register( String id, String name, AsyncContext ctx ) throws IOException {
        lock.lock();
        try {
            if( !guests.containsKey( id ) )
            {
                Person newGuest = new Person( id, name, ctx );
                guests.put( id, newGuest );
                for( Person person : guests.values() ) {
                    if( person.equals( newGuest ) ){
                        person.notify( new Message( 
                                String.format( "%s be welcome!", person.getName() ) ) );
                    } else {
                        person.notify( new Message( 
                                String.format( "\r\n%s entered the Hall! Salute!", newGuest.getName() ) ) );
                    }
                }
            }
            else
            {
                Person person = guests.get( id );
                person.setAsyncContext( ctx );
                person.notify( new Message("") );
            }
        } finally {
            lock.unlock();
        }

    }

    void speak( String id, String phrase ) throws IOException {
        lock.lock();
        try {
            Person currentSpeaker = guests.get( id );
            String message = String.format( "\r\n%s: %s", currentSpeaker.getName(), phrase ); 
            for ( Person person : guests.values() ) {
                person.notify( new Message( message ) );
            }
        } finally {
            lock.unlock();
        }
    }

    void bind( String id, AsyncContext ctx ) throws IOException {
        //need to find a good way to avoid the client to rebind
        //if it was no registered or there is an error
        Person person = guests.get( id );
        if( person != null ) {
            person.setAsyncContext( ctx );
        }
    }

    void leave( String id ) throws IOException {
        lock.lock();
        try {
            if( guests.containsKey( id ) )
            {
                Person leavingGuest = guests.get( id );
                leavingGuest.notify( new Message( "\r\nYou left the hall..." ) );
                guests.remove( id );
                String message = String.format( "\r\n%s left the hall...", leavingGuest.getName() ); 
                for ( Person person : guests.values() ) {
                    person.notify( new Message( message ) );
                }
            } 
        } finally {
            lock.unlock();
        }
    }
}
