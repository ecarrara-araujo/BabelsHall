package br.com.ecarrara.bh.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

/**
 *
 * @author ecarrara
 */
@JsonInclude( JsonInclude.Include.NON_NULL )
public class Message {

    private String message;
    private List<Person> participants;
    
    public Message( String message ) {
        this.message = message;
    }

    public Message( List<Person> participants ) {
        this.participants = participants;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage( String message ) {
        this.message = message;
    }

    public List<Person> getParticipants() {
        return participants;
    }

    public void setParticipants( List<Person> participants ) {
        this.participants = participants;
    }
}
