``` In Java, a source file can have at most one  class, and if that  class exists, the file name must match that class name (plus the  extension). ```
## Key Rules in Java

• One Public Class Limit: You cannot declare two or more  classes in a single  file. Doing so causes a compilation error.
• Name Match: If you have a , your file must be saved as .
• Non-Public Classes: You can still include multiple package-private (default) classes in the same file. They do not need to match the file name.
• No Public Class Option: If a file contains no  classes at all, you can name the file whatever you want, and the code will still compile and run.

## Why This Rule Exists

• Fast Compilation: The compiler uses the file name to quickly locate the main  class inside packages without scanning every single line of code.
• Clean Organization: It keeps projects structured so that anyone looking at the file system instantly knows what primary component a file holds.

Would you like to see a code example showing how to put multiple non-public classes inside a single Java file?
AI responses may include mistakes.

