package br.com.ecarrara.bh.entities;

/**
 *
 * @author ecarrara
 */
public class Statement {
    
    private String phrase;

    public Statement( String phrase ) {
        this.phrase = phrase;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase( String phrase ) {
        this.phrase = phrase;
    }
}
