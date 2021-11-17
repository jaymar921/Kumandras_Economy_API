package me.jaymar921.kumandraseconomy.utility;

public class LangParse {

    public static String string(String message, String add){
        String[] msg = message.split("%");
        if(msg.length==2)
            return msg[0] + add + msg[1];
        if(msg.length==1){
            if(msg[0]!=null)
                return msg[0] + add;
        }
        return message;
    }
}
