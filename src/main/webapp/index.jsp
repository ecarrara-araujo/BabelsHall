<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Babel's Hall</title>
        <script type="text/javascript" src="resources/js/jquery-1.7.2.min.js"></script>
        <script type="text/javascript" src="resources/js/knockout-2.0.0.js"></script>
        <script type="text/javascript" src="resources/js/jquery.caret.js"></script>
    </head>
    <body>
        <h1>Babel's Hall</h1>
        <div id="guest">
            <h2>Be welcome! Register and start typing.</h2>
            <h2>Insert your name and click register to begin:</h2>
            <input type="text" name="guest" data-bind="value: guest"/>
            <input type="button" value="register" data-bind="click: register" />
            <input type="button" value="leave" data-bind="click: leave" />
        </div>
        <br/>
        <div id="hall">
            <textarea id="dialogBox" rows="4" cols="50" readonly="readonly" data-bind="text: dialog">Bla bla bla</textarea>
        </div>
        <br/>
        <div id="typewritter">
            <input type="text" name="phrase" data-bind="value: phrase"/>
            <input type="button" value="send" data-bind="click: speak" />
        </div>
        <script>
            var BabelsHall = function() {
                var self = this;
                self.guest = ko.observable();
                self.dialog = ko.observable();
                self.phrase = ko.observable();
                self.dialog('');
                
                self.bind = function() {
                    $.getJSON("/babelshall", {action: "bind"}, function(data) {
                        self.parseResult(data);
                    }).complete(function() {
                        self.bind();
                    });
                }
                
                self.register = function() {
                    $.getJSON("/babelshall", {action: "register", name: self.guest()}, function(data) {
                        self.parseResult(data);
                        self.bind();
                    });
                }
                
                self.leave = function() {
                    $.getJSON("/babelshall", {action: "leave" }, function(data) {
                        self.parseResult(data);
                    });
                }

                self.speak = function() {
                    $.getJSON("/babelshall", {action: "speak", phrase: self.phrase() }, function(data){
                        self.parseResult(data);
                        self.phrase('');
                    });
                    return true;
                }
                
                self.parseResult = function( data ) {
                    if( data != null ) {
                        self.dialog( self.dialog() + data.message);
                        $('#dialogBox').caretToEnd();
                    }
                }
            }
            
            ko.applyBindings(new BabelsHall());
        </script>
    </body>
</html>
