import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Input {
    public static int Nest=0;
    static ArrayList<Word> Words=new ArrayList<Word>();
    static ArrayList<String> varKey=new ArrayList<String>();
    static ArrayList<String> funcKey=new ArrayList<String>();
    static ArrayList<String> classKey=new ArrayList<String>();

    static ArrayList<Word> Read(String InputFile) {

        Scanner input = null;
        try {
            input = new Scanner((new File(InputFile)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        ArrayList<String> WordList=new ArrayList<>();
        String Word = "";
        String PrevChar="";
        String PrevNonWhiteSpace="";
        int Quote=0;

        while (input.hasNextLine()) {
            String CurrentLine=input.nextLine();
            CurrentLine=CurrentLine.trim();

            if(CurrentLine.equals("")){
                continue;
            }


            for (int i=0;i<CurrentLine.length();i++){
                String CurrentChar = Character.toString(CurrentLine.charAt(i));
                if (i>0){
                    PrevChar = Character.toString(CurrentLine.charAt(i-1));

                }


                if(CurrentChar.equals("\"") && !PrevChar.equals("\\")){
                    if (Quote==1){
                        Quote=0;
                    }
                    else {
                        Quote++;
                    }
                }



                if (CurrentChar.equals(";") && Quote==0){
                    Word = Word + CurrentChar;
                    WordList.add(Word);
                    Word = "";
                    continue;
                }

                if (CurrentChar.equals("{") && Quote==0 && !PrevNonWhiteSpace.equals("]")){
                    Word = Word + CurrentChar;
                    WordList.add(Word);
                    Word = "";
                    continue;
                }

                if (CurrentChar.equals("}") && Quote==0){
                    Word = Word + CurrentChar;
                    WordList.add(Word);
                    Word = "";
                    continue;
                }


                Word = Word + CurrentChar;

                if(!CurrentChar.equals(" ")){
                    PrevNonWhiteSpace=CurrentChar;
                }
            }
        }

        for (int i=0;i<WordList.size();i++){
            String CurrentLine=WordList.get(i);
            CurrentLine=CurrentLine.trim();

            if(CurrentLine.equals("")){
                continue;
            }

            ArrayList<String> CharList=new ArrayList<>();



            for (int j=0;j<CurrentLine.length();j++){
                CharList.add(Character.toString(CurrentLine.charAt(j)));
            }

            String Type = KeywordType(CurrentLine);
            Words.add(new Word(CurrentLine,Type,Nest,CharList));
            NestCheck(CharList);
            //CharList.clear();
        }



        return Words;
    }

    static String KeywordType(String Word){
        String KeywordType;
        String[] Keywords=new String[]{
                "System.out.print.+","if.+","\\{","\\}",
                "for.+","float.+","String\\[\\].+","int\\[\\].+",
                "while.+",
                "try.+", "catch.+", "break.+", "continue.+",
                "return.+", "this.+",
                "finally.+","else if.+","else.+",
                "Math.min.+","Math.max.+","Math.sqrt.+", ";", "public static void main.+",
        };

        String[] KeywordsClass=new String[]{
              "public Class.+", "protected Class.+",
              "private Class.+","Class.+"};

        String[] KeywordsFunc  = new String[]{
              "public int \\w+\\(.+", "protected int \\w+\\(.+",
              "private int \\w+\\(.+", "int \\w+\\(.+",
              "public String \\w+\\(.+", "protected String \\w+\\(.+",
              "private String \\w+\\(.+", "String \\w+\\(.+",
              "public double \\w+\\(.+" , "protected double \\w+\\(.+" ,
              "private double \\w+\\(.+" ,"double \\w+\\(.+",
              "public boolean \\w+\\(.+", "protected boolean \\w+\\(.+",
              "private boolean \\w+\\(.+", "boolean \\w+\\(.+",
              };
        String[] KeywordsVar  = new String[]{
        		"int.+", "static int.+", "public int.+", "private int.+", "public static int.+", "private static int.+", "protected int.+",
                "String.+", "static String.+", "public String.+", "private String.+", "public static String.+", "private static String.+", "protected String.+", 
                "double.+", "static double.+", "public double.+", "private double.+", "public static double.+", "private static double.+", "protected double.+",
                "Double.+", "static Double.+", "public Double.+", "private Double.+", "public static Double.+", "private static Double.+", "protected Double.+",
                "boolean.+", "static boolean.+", "public boolean.+", "private boolean.+", "public static boolean.+", "private static boolean.+", "protected boolean.+",
                "Boolean.+", "static Boolean.+", "public Boolean.+", "private Boolean.+", "public static Boolean.+", "private static Boolean.+", "protected Boolean.+",
              };



        for (int i=0;i<Keywords.length;i++){
            if(Word.matches(Keywords[i])){
                KeywordType=Keywords[i];
                if (Keywords[i].equals("public static void main.+")){
                    Nest--;
                }
                return KeywordType;
            }
        }

        for (int k=0;k<KeywordsFunc.length; k++)
        {
          if(Word.matches(KeywordsFunc[k])){
              KeywordType=KeywordsFunc[k];
              addFunction(Word, funcKey);
              return KeywordType;
            }

        }

      for (int j=0;j<KeywordsVar.length; j++)
      {
        if(Word.matches(KeywordsVar[j])){
            KeywordType=KeywordsVar[j];
            addVariable(Word, varKey);
            return KeywordType;
          }

      }


      for (int l=0;l<KeywordsClass.length; l++)
      {
        if(Word.matches(KeywordsClass[l])){
            KeywordType=KeywordsClass[l];
            addClass(Word, classKey);
            return KeywordType;
          }

      }

      if(checkFunc(Word))
      {
        KeywordType = "function";
        return KeywordType;
      }
      else if(checkVar(Word))
      {
        KeywordType = "variable";
        return KeywordType;
      }
      else if(checkClass(Word))
      {
        KeywordType = "class";
        return KeywordType;
      }

        return "NULL";
    }

    static void NestCheck(ArrayList<String> CharList){



        for (int i=0;i<CharList.size();i++){
            String Prev2Char="";

            if (i>1){
                Prev2Char=CharList.get(i-2);
                Prev2Char = Prev2Char + CharList.get(i-1);
            }

            String CurrentChar = CharList.get(i);

            if (CurrentChar.equals("{") && !Prev2Char.equals("\\\\")){
                Nest++;
            }

            if (CurrentChar.equals("}") && !Prev2Char.equals("\\\\")){
                if (Nest>0)
                Nest--;
            }
        }

    }

    static void addVariable(String word, ArrayList<String> var)
  {
    String wordCopy = word;
    String access = "";
    String name = "";

    if (wordCopy.startsWith("protected"))
    {
      access = " _";
      wordCopy=wordCopy.replaceFirst("protected \\w+ ","");
      wordCopy = wordCopy.substring(0, wordCopy.length()-1);
      name = wordCopy.split(" ")[0];
    }
    else if (wordCopy.startsWith("private"))
    {
      access = " __";
      wordCopy=wordCopy.replaceFirst("private \\w+ ","");
      wordCopy = wordCopy.substring(0, wordCopy.length()-1);
      name = wordCopy.split(" ")[0];
    }
    else if (wordCopy.startsWith("public"))
    {
      wordCopy=wordCopy.replaceFirst("public \\w+ ","");
      wordCopy = wordCopy.substring(0, wordCopy.length()-1);
      name = wordCopy.split(" ")[0];
    }
    else
    {
      wordCopy = wordCopy.replaceFirst("\\w+ ","");
      wordCopy = wordCopy.substring(0, wordCopy.length()-1);
      name = wordCopy.split(" ")[0];
    }

    name = name + access;

    var.add(name);
  }

  static void addFunction(String word, ArrayList<String> func)
  {
    String wordCopy = word;
    String access = "";
    String name = "";
    int split = 0;

    if (wordCopy.startsWith("protected"))
    {
      access = " _";
      wordCopy=wordCopy.replaceFirst("protected \\w+ ", "");
      split = wordCopy.indexOf("(");
      name = wordCopy.substring(0, split+1);
    }
    else if (wordCopy.startsWith("private"))
    {
      access = " __";
      wordCopy=wordCopy.replaceFirst("private \\w+ ","");
      split = wordCopy.indexOf("(");
      name = wordCopy.substring(0, split+1);
    }
    else if (wordCopy.startsWith("public"))
    {
      wordCopy=wordCopy.replaceFirst("public \\w+ ","");
      split = wordCopy.indexOf("(");
      name = wordCopy.substring(0, split+1);
    }
    else
    {
      split = wordCopy.indexOf("(");
      name = wordCopy.substring(0, split+1);

    }

    name = name + access;

    func.add(name);
  }

  static void addClass(String word, ArrayList<String> cla)
  {
    String wordCopy = word;
    String access = "";
    String name = "";
    int split = 0;

    if (wordCopy.startsWith("protected"))
    {
      access = " _";
      wordCopy=wordCopy.replaceFirst("protected Class ", "");
      split = wordCopy.indexOf("{");
      name = wordCopy.substring(0, split);
    }
    else if (wordCopy.startsWith("private"))
    {
      access = " __";
      wordCopy=wordCopy.replaceFirst("private Class ","");
      split = wordCopy.indexOf("{");
      name = wordCopy.substring(0, split);
    }
    else if (wordCopy.startsWith("public"))
    {
      wordCopy=wordCopy.replaceFirst("public Class ","");
      split = wordCopy.indexOf("{");
      name = wordCopy.substring(0, split);
    }
    else
    {
      wordCopy=wordCopy.replaceFirst("Class ","");
      split = wordCopy.indexOf("{");
      name = wordCopy.substring(0, split);

    }

    name = name + access;

    cla.add(name);
  }

  static boolean checkFunc(String word)
  {
    for(int i=0; i<funcKey.size(); i++)
    {
      if(word.startsWith(funcKey.get(i)))
      {
        return true;
      }
    }

    return false;
  }

  static boolean checkVar(String word)
  {
    for(int i=0; i<varKey.size(); i++)
    {
      if(word.startsWith(varKey.get(i)))
      {
        return true;
      }
    }

    return false;
  }

  static boolean checkClass(String word)
  {
    for(int i=0; i<classKey.size(); i++)
    {
      if(word.startsWith(classKey.get(i)))
      {
        return true;
      }
    }

    return false;
  }


}
