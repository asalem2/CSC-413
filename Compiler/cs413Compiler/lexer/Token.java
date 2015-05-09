package lexer;

/** <pre>
 *  The Token class records the information for a token:
 *  1. The Symbol that describes the characters in the token
 *  2. The starting column in the source file of the token and
 *  3. The ending column in the source file of the token
 *  </pre>
*/
public class Token {
  private int leftPosition,rightPosition;
  private Symbol symbol;

/**
 *  Create a new Token based on the given Symbol
 *  @param leftPosition is the source file column where the Token begins
 *  @param rightPosition is the source file column where the Token ends
*/
  public Token(int leftPosition, int rightPosition, Symbol sym) {
   this.leftPosition = leftPosition;
   this.rightPosition = rightPosition;
   this.symbol = sym;
  }

  /**
   @return The symbol of the type we're scanning
  */
  public Symbol getSymbol() {
    return symbol;
  }

    /**
     * Prints the left and right position of the token that was scanned
    */
  public void print() {
    return;
  }

    /**
     @return A string of the calling symbol
     */
  public String toString() {
    return symbol.toString();
  }

    /**
     @return The left position of where the token starts
     */
  public int getLeftPosition() {
    return leftPosition;
  }

    /**
     @return The right position of where the token ends
     */
  public int getRightPosition() {
    return rightPosition;
  }


/**
 *  @return the integer that represents the kind of symbol we have which
 *  is actually the type of token associated with the symbol
*/
  public Tokens getKind() {
    return symbol.getKind();
  }
}

