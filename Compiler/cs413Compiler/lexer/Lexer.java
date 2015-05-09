package lexer;

import java.util.*;

/**
 *  The Lexer class is responsible for scanning the source file
 *  which is a stream of characters and returning a stream of 
 *  tokens; each token object will contain the string (or access
 *  to the string) that describes the token along with an
 *  indication of its location in the source program to be used
 *  for error reporting; we are tracking line numbers; white spaces
 *  are space, tab, newlines
*/
public class Lexer {
    private boolean atEOF = false;
    private char ch;   // next character to process
    private SourceReader source;
    // positions in line of current token
    private int startPosition, endPosition;

    public Lexer(String sourceFile) throws Exception {
        new TokenType();  // init token table
        source = new SourceReader(sourceFile);
        ch = source.read();
    }


    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);
        Token tok;
        System.out.print("Enter File Name: ");
        String name = input.nextLine();
        int m = 0;
        int arrayK[] = new int[50];
        while(m < arrayK.length) {
            arrayK[m] = 0;
            m++;
        }
        int currentL = 1;
        int track = 0;
        LinkedList<Token> program = new LinkedList<Token>(); // store parsed tokens in a LinkedList for later printing
        try {
            Lexer lex = new Lexer(name);
            while (true){
                tok = lex.nextToken();
                program.add(tok);
                String p =  TokenType.tokens.get(tok.getKind()) + "";
                if ((tok.getKind() == Tokens.Identifier) || (tok.getKind() == Tokens.INTeger) || (tok.getKind() == Tokens.Floating) || (tok.getKind() == Tokens.Character)||(tok.getKind() == Tokens.scientificN))
                    p += ":" + tok.toString();
                p +=  "\t";
                p +="Left: " + tok.getLeftPosition() + " Right : " + tok.getRightPosition() + "  ";
                System.out.println(p + "Line: " + lex.source.getLineno());
                if (currentL == lex.source.getLineno()){
                    arrayK[track] = arrayK[track] + 1;
                }
                else{
                    currentL = lex.source.getLineno();
                    track++;
                    arrayK[track] = arrayK[track] + 1;
                }
            }
        }
        catch (Exception e) {
            System.out.println("\n");
            int i = 0;
            int g = 0;
            int index = 0;
            while (i < (program.size()-1)) {
                int h = 0;
                if (arrayK[g] != 0){
                    System.out.print(i + 1 + ". ");
                    while (h < arrayK[g]) {
                        System.out.print(program.get(index) + " ");
                        h++;
                        index++;
                    }
                    System.out.print("\n");
                }
                g++;
                i++;
            }
        }
    }

 
/**
 *  newIdTokens are either ids or reserved words; new id's will be inserted
 *  in the symbol table with an indication that they are id's
 *  @param id is the String just scanned - it's either an id or reserved word
 *  @param startPosition is the column in the source file where the token begins
 *  @param endPosition is the column in the source file where the token ends
 *  @return the Token; either an id or one for the reserved words
*/
    public Token newIdToken(String id,int startPosition,int endPosition) {
        return new Token(startPosition,endPosition,Symbol.symbol(id,Tokens.Identifier));
    }

/**
 *  number tokens are inserted in the symbol table; we don't convert the 
 *  numeric strings to numbers until we load the bytecodes for interpreting;
 *  this ensures that any machine numeric dependencies are deferred
 *  until we actually run the program; i.e. the numeric constraints of the
 *  hardware used to compile the source program are not used
 *  @param number is the int String just scanned
 *  @param startPosition is the column in the source file where the int begins
 *  @param endPosition is the column in the source file where the int ends
 *  @return the int Token
*/
    public Token newNumberToken(String number,int startPosition,int endPosition) {
        return new Token(startPosition,endPosition,
            Symbol.symbol(number,Tokens.INTeger));
    }

    /**
     *  float tokens are inserted in the symbol table; we don't convert the
     *  numeric strings to numbers until we load the bytecodes for interpreting;
     *  @param number is the float String just scanned
     *  @param startPosition is the column in the source file where the int begins
     *  @param endPosition is the column in the source file where the int ends
     *  @return the float Token
     */
    public Token newNumberTokenF(String number,int startPosition,int endPosition) {
        return new Token(startPosition,endPosition,
                Symbol.symbol(number,Tokens.Floating));
    }
    /**
     *  newCharTokens are char types being inserted
     *  in the symbol table with an indication that they are char types
     *  @param c is the String just scanned
     *  @param startPosition is the column in the source file where the token begins
     *  @param endPosition is the column in the source file where the token ends
     *  @return the Char token
     */
    public Token newCharToken(String c,int startPosition,int endPosition) {
        return new Token(startPosition,endPosition,
                Symbol.symbol(c,Tokens.Character));
    }
    /**
     *  float tokens are inserted in the symbol table;This float tokens
     *  are in Scientific Notation.
     *  @param number is the scientificN String just scanned
     *  @param startPosition is the column in the source file where the int begins
     *  @param endPosition is the column in the source file where the int ends
     *  @return the ScientificN Token
     */
    public Token newNumberTokenSci(String number,int startPosition,int endPosition) {
        return new Token(startPosition,endPosition,
                Symbol.symbol(number,Tokens.scientificN));
    }

/**
 *  build the token for operators (+ -) or separators (parens, braces)
 *  filter out comments which begin with two slashes
 *  @param s is the String representing the token
 *  @param startPosition is the column in the source file where the token begins
 *  @param endPosition is the column in the source file where the token ends
 *  @return the Token just found
*/
    public Token makeToken(String s,int startPosition,int endPosition) {
        if (s.equals("//")) {  // filter comment
            try {
               int oldLine = source.getLineno();
               do {
                   ch = source.read();
               } while (oldLine == source.getLineno());
            } catch (Exception e) {
                    atEOF = true;
            }
            return nextToken();
        }
        Symbol sym = Symbol.symbol(s,Tokens.BogusToken); // be sure it's a valid token
        if (sym == null) {
             System.out.println("\n******** illegal character: " + s + " on line " + source.getLineno());
             atEOF = true;
             return nextToken();
        }
        return new Token(startPosition,endPosition,sym);
    }

/**
 * This class checks what the type of the next token read is
 *  @return the next Token found in the source file
*/
    public Token nextToken() { // ch is always the next char to process
        if (atEOF) {
            if (source != null) {
                source.close();
                source = null;
            }
            return null;
        }
        try {
            while (Character.isWhitespace(ch)) {  // scan past whitespace
                ch = source.read();
            }
        } catch (Exception e) {
            atEOF = true;
            return nextToken();
        }
        startPosition = source.getPosition();
        endPosition = startPosition - 1;

        if (Character.isDigit(ch)) {
            int dot = 0;
            int sci = 0;
            // return number tokens
            String number = "";
            try {
                do {
                    endPosition++;
                    number += ch;
                    ch = source.read();
                    char temp = ch;
                    if (ch == '.' && dot == 0) {
                        endPosition++;
                        dot = 1;
                        number += ch;
                        ch = source.read();
                    }
                    if (ch == 'e' || ch == 'E') {
                        endPosition++;
                        ch = source.read();
                        if (ch == '0')
                            ch = 'k';
                        if(ch == ' ') {
                            int tempP = source.getPosition();
                            int tempL = source.getLineno();
                            source.modify(tempP,tempL);
                            ch = 'e';
                        }
                        if(ch == '-' || ch == '+' ||  Character.isDigit(ch) ){
                            number += temp;
                            sci = 1;
                        }
                        else if ( ch != 'e'){
                            System.out.println("\nThere must be a digit or +/- operator after 'e' on line" + source.getLineno());
                            atEOF = true;
                            return nextToken();
                        }
                    }
                } while (Character.isDigit(ch) || ch == '+' ||ch == '-');
            } catch (Exception e) {
                atEOF = true;
            }
            if (sci == 1)
                return newNumberTokenSci(number, startPosition, endPosition);
            else if (dot != 0)
                return newNumberTokenF(number, startPosition, endPosition);
            else if(ch == 'e' || ch == ' ')
                return newNumberToken(number,startPosition,endPosition);
        }
        if (Character.isJavaIdentifierStart(ch)) {
            // return tokens for ids and reserved words
            String id = "";
            try {
                do {
                    endPosition++;
                    id += ch;
                    ch = source.read();
                } while (Character.isJavaIdentifierPart(ch));
            } catch (Exception e) {
                atEOF = true;
            }
            return newIdToken(id,startPosition,endPosition);
        }

        if (ch == '.') {
            int check = 0;
            // return number tokens
            String number = "";
            try {
                do {
                    endPosition++;
                    number += ch;
                    ch = source.read();
                    if ((ch == '.' || ch == 'e' || ch == ' ') && check == 0){
                        System.out.println("\nThere must be a digit before or after the '.' on line" + source.getLineno());
                        atEOF = true;
                        return nextToken();
                    }
                    else{
                        check = 1;
                    }
                } while (Character.isDigit(ch));
            } catch (Exception e) {
                atEOF = true;
            }
            return newNumberTokenF(number, startPosition, endPosition);
        }
        if (ch == '\'') {
            int check = 0;
            // return number tokens
            String c = "";
            try {
                do {
                     if (ch != '\'' && check == 0) {
                         endPosition++;
                         c += ch;
                         check = 1;
                     }
                    else if (ch != '\'' && check != 0 && ch != ' '){
                         System.out.println("\nError on line " + source.getLineno() + ". Char can only have one character (e.g char ex = 'g')");
                         atEOF = true;
                         return nextToken();
                     }
                     else if (ch == ' '){
                         System.out.println("\nChar type should have two single quotes '' (e.g char ex = 'g') on line " + source.getLineno());
                         atEOF = true;
                         return nextToken();
                     }

                    ch = source.read();
                } while (ch != '\'');
            } catch (Exception e) {
                atEOF = true;
            }
            ch = ' ';
            return newCharToken(c, startPosition, endPosition);
        }
        
        // At this point the only tokens to check for are one or two
        // characters; we must also check for comments that begin with
        // 2 slashes
        String charOld = "" + ch;
        String op = charOld;
        Symbol sym;
        try {
            endPosition++;
            ch = source.read();
            op += ch;
            // check if valid 2 char operator; if it's not in the symbol
            // table then don't insert it since we really have a one char
            // token
            sym = Symbol.symbol(op, Tokens.BogusToken); 
            if (sym == null) {  // it must be a one char token
                return makeToken(charOld,startPosition,endPosition);
            }
            endPosition++;
            ch = source.read();
            return makeToken(op,startPosition,endPosition);
        } catch (Exception e) {}
        atEOF = true;
        if (startPosition == endPosition) {
            op = charOld;
        }
        return makeToken(op,startPosition,endPosition);
    }
}