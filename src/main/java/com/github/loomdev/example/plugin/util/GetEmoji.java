package com.github.loomdev.example.plugin.util;

public class GetEmoji{

    /*
     * Using a algorithm that no one can understand
     * this class turns a simple in text command from
     * :joy: to (*≧▽≦)
     */

    public static String emoji(final String message){
        final String[] expressions = {
                "(*≧▽≦)", "୧((#Φ益Φ#))୨", "(╥﹏╥)", "(＋_＋)",
                "(♥ω♥*)", "( ◡‿◡ )"
        };
        return message.replace("joy", expressions[0]).replace("rage", expressions[1])
                .replace("cry", expressions[2]).replace("doubt", expressions[3])
                .replace("love", expressions[4]).replace("bliss", expressions[5]);
    }

}
