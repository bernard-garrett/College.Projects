import java.util.ArrayList;

public class Word {
    String RawInput;
    String Type;
    int Nest;
    ArrayList<String> CharList;

    public Word(String RI,String T, int N, ArrayList<String> CL){
        RawInput=RI;
        Type=T;
        Nest=N;
        CharList=CL;
    }
}
