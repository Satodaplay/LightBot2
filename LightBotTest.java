import org.testng.annotations.Test;
import static org.junit.Assert.*;

public class LightBotTest {
    @Test
    public void test01() {
        LightBot lb = new LightBot(new String[]{
                ".....O..",
                "........",
                "........",
                "..R..O.."
        });

        lb.reset();
        lb.runProgram(new String[]{
                "FORWARD",
                "FORWARD",
                "FORWARD",
        });

        assertArrayEquals(new int[]{5,3}, lb.getRobotPosition());
        assertArrayEquals(new String[]{
                ".....O..",
                "........",
                "........",
                ".....O.."
        }, lb.getMap());

        lb.reset();
        lb.runProgram(new String[]{
                "FORWARD",
                "FORWARD",
                "FORWARD",
                "LIGHT",
        });

        assertArrayEquals(new int[]{5,3}, lb.getRobotPosition());

        assertArrayEquals(new String[]{
                ".....O..",
                "........",
                "........",
                ".....X.."
        }, lb.getMap());

        lb.reset();
        lb.runProgram(new String[]{
                "LEFT",
                "FORWARD",
                "FORWARD",
        });

        assertArrayEquals(new int[]{2,1}, lb.getRobotPosition());

        assertArrayEquals(new String[]{
                ".....O..",
                "........",
                "........",
                ".....O.."
        }, lb.getMap());

        lb.reset();
        lb.runProgram(new String[]{
                "LEFT",
                "FORWARD",
                "FORWARD",
                "FORWARD",
                "RIGHT",
                "FORWARD",
                "FORWARD",
                "FORWARD",
                "LIGHT",
                "FORWARD",
        });

        assertArrayEquals(new int[]{6,0}, lb.getRobotPosition());

        assertArrayEquals(new String[]{
                ".....X..",
                "........",
                "........",
                ".....O.."
        }, lb.getMap());

        lb.reset();
        lb.runProgram(new String[]{
                "LEFT",
                "FORWARD",
                "FORWARD",
                "FORWARD",
                "RIGHT",
                "FORWARD",
                "FORWARD",
                "FORWARD",
                "LIGHT",
                "FORWARD",
                "RIGHT",
                "FORWARD",
                "FORWARD",
                "FORWARD",
                "RIGHT",
                "FORWARD",
                "LIGHT",
        });

        assertArrayEquals(new int[]{5,3}, lb.getRobotPosition());

        assertArrayEquals(new String[]{
                ".....X..",
                "........",
                "........",
                ".....X.."
        }, lb.getMap());
    }

    @Test
    public void test02() {
        LightBot lb = new LightBot(new String[]{
                "..L..O..",
                "........",
                "........",
                ".....O.."
        });

        lb.reset();
        lb.runProgram(new String[]{
                "FORWARD",
                "FORWARD",
                "FORWARD",
        });

        assertArrayEquals(new int[]{7,0}, lb.getRobotPosition());
        assertArrayEquals(new String[]{
                ".....O..",
                "........",
                "........",
                ".....O.."
        }, lb.getMap());

        lb.reset();
        lb.runProgram(new String[]{
                "LEFT",
                "FORWARD",
                "FORWARD",
                "FORWARD",
                "LEFT",
                "FORWARD",
                "FORWARD",
                "FORWARD",
                "LIGHT",
        });
        assertArrayEquals(new int[]{5,3}, lb.getRobotPosition());
        assertArrayEquals(new String[]{
                ".....O..",
                "........",
                "........",
                ".....X.."
        }, lb.getMap());

        lb.reset();
        lb.runProgram(new String[]{
                "FORWARD",
                "FORWARD",
                "FORWARD",
                "LEFT",
                "FORWARD",
                "FORWARD",
                "LEFT",
                "FORWARD",
                "LEFT",
                "FORWARD",
        });
        assertArrayEquals(new int[]{0,1}, lb.getRobotPosition());
        assertArrayEquals(new String[]{
                ".....O..",
                "........",
                "........",
                ".....O.."
        }, lb.getMap());

        lb.reset();
        lb.runProgram(new String[]{
                "LEFT",
                "FORWARD",
                "FORWARD",
                "FORWARD",
                "LEFT",
                "FORWARD",
                "FORWARD",
                "FORWARD",
                "LIGHT",
                "LEFT",
                "FORWARD",
                "FORWARD",
                "FORWARD",
                "LIGHT",
        });
        assertArrayEquals(new int[]{5,0}, lb.getRobotPosition());
        assertArrayEquals(new String[]{
                ".....X..",
                "........",
                "........",
                ".....X.."
        }, lb.getMap());
    }

    @Test
    public void test03() {
        LightBot lb = new LightBot(new String[]{
                "..R..O..",
                ".....O..",
                ".....O..",
                ".....O.."
        });

        lb.reset();
        lb.runProgram(new String[]{
                "FORWARD",
                "FORWARD",
                "FORWARD",
                "LIGHT",
                "RIGHT",
                "REPEAT 3", "LIGHT", "FORWARD", "ENDREPEAT",
                "LIGHT",
        });

        assertArrayEquals(new int[]{5,3}, lb.getRobotPosition());
        assertArrayEquals(new String[]{
                ".....X..",
                ".....X..",
                ".....X..",
                ".....X.."
        }, lb.getMap());

        lb.reset();
        lb.runProgram(new String[]{
                "FORWARD",
                "FORWARD",
                "FORWARD",
                "LIGHT",
                "RIGHT",
                "REPEAT 4", "LIGHT", "FORWARD", "ENDREPEAT",
        });

        assertArrayEquals(new int[]{5,0}, lb.getRobotPosition());
        assertArrayEquals(new String[]{
                ".....X..",
                ".....X..",
                ".....X..",
                ".....X.."
        }, lb.getMap());
    }

    @Test
    public void test04() {
        LightBot lb = new LightBot(new String[]{
                ".....O..",
                "........",
                ".....O..",
                "........",
                "L....O..",
                "........",
                ".....O..",
                "........",
                ".....O..",
                "........",

        });

        lb.reset();
        lb.runProgram(new String[]{
                "LEFT",
                "LEFT",
                "REPEAT 5", "FORWARD", "ENDREPEAT",
                "RIGHT",
                "REPEAT 2", "LIGHT", "FORWARD", "FORWARD", "ENDREPEAT",
                "LIGHT"
        });

        assertArrayEquals(new int[]{5,8}, lb.getRobotPosition());
        assertArrayEquals(new String[]{
                ".....O..",
                "........",
                ".....O..",
                "........",
                ".....X..",
                "........",
                ".....X..",
                "........",
                ".....X..",
                "........",
        }, lb.getMap());

        lb.reset();
        lb.runProgram(new String[]{
                "LEFT", "LEFT",
                "REPEAT 5", "FORWARD", "ENDREPEAT",
                "RIGHT",
                "REPEAT 2", "LIGHT", "FORWARD", "FORWARD", "ENDREPEAT",
                "LIGHT",
                "RIGHT", "RIGHT",
                "REPEAT 6", "FORWARD", "ENDREPEAT",
                "LIGHT",
                "FORWARD",
                "FORWARD",
                "LIGHT",
        });

        assertArrayEquals(new int[]{5,0}, lb.getRobotPosition());
        assertArrayEquals(new String[]{
                ".....X..",
                "........",
                ".....X..",
                "........",
                ".....X..",
                "........",
                ".....X..",
                "........",
                ".....X..",
                "........",
        }, lb.getMap());
    }

    @Test
    public void test05() {
        LightBot lb = new LightBot(new String[]{
                "...OOO..",
                "........",
                "...OOO..",
                "........",
                "...OOO..",
                "........",
                "...OOO..",
                "........",
                "...OOO..",
                "...R....",

        });

        lb.reset();
        lb.runProgram(new String[]{
                "LEFT",
                "REPEAT 2",
                    "FORWARD", "RIGHT",
                    "REPEAT 3", "LIGHT", "FORWARD", "ENDREPEAT",
                    "RIGHT", "RIGHT",
                    "REPEAT 3", "FORWARD", "ENDREPEAT",
                    "RIGHT", "FORWARD",
                "ENDREPEAT",
        });

        assertArrayEquals(new int[]{3,5}, lb.getRobotPosition());
        assertArrayEquals(new String[]{
                "...OOO..",
                "........",
                "...OOO..",
                "........",
                "...OOO..",
                "........",
                "...XXX..",
                "........",
                "...XXX..",
                "........",
        }, lb.getMap());

        lb.reset();
        lb.runProgram(new String[]{
                "LEFT",
                "REPEAT 4",
                    "FORWARD", "RIGHT",
                    "REPEAT 3", "LIGHT", "FORWARD", "ENDREPEAT",
                    "RIGHT", "RIGHT",
                    "REPEAT 3", "FORWARD", "ENDREPEAT",
                    "RIGHT", "FORWARD",
                "ENDREPEAT",
                "FORWARD",
                "RIGHT",
                "REPEAT 3", "LIGHT", "FORWARD", "ENDREPEAT",
        });

        assertArrayEquals(new int[]{6,0}, lb.getRobotPosition());
        assertArrayEquals(new String[]{
                "...XXX..",
                "........",
                "...XXX..",
                "........",
                "...XXX..",
                "........",
                "...XXX..",
                "........",
                "...XXX..",
                "........",
        }, lb.getMap());
    }

    @Test
    public void test06() {
        LightBot lb = new LightBot(new String[]{
                "OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO",
                "R..........................................",
                "OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO",
                "...........................................",
                "OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO",
                "...........................................",
                "OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO",
                "...........................................",
                "OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO",
                "...........................................",
                "OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO",
                "...........................................",
                "OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO",
                "...........................................",
        });

        lb.reset();
        lb.runProgram(new String[]{
                "LEFT", "FORWARD", "RIGHT",
                "REPEAT 7",
                    "REPEAT 42", "LIGHT", "FORWARD", "ENDREPEAT",
                    "LIGHT", "LEFT", "LEFT",
                    "REPEAT 42", "FORWARD", "ENDREPEAT",
                    "LEFT", "FORWARD", "FORWARD", "LEFT",
                "ENDREPEAT"
        });

        assertArrayEquals(new int[]{0, 0}, lb.getRobotPosition());
        assertArrayEquals(new String[]{
                "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
                "...........................................",
                "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
                "...........................................",
                "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
                "...........................................",
                "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
                "...........................................",
                "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
                "...........................................",
                "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
                "...........................................",
                "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
                "...........................................",
        }, lb.getMap());

        lb.reset();
        lb.runProgram(new String[]{
                "LEFT", "FORWARD", "RIGHT",
                "REPEAT 7",
                "REPEAT 21", "LIGHT", "FORWARD", "FORWARD", "ENDREPEAT",
                "LIGHT", "LEFT", "LEFT",
                "REPEAT 42", "FORWARD", "ENDREPEAT",
                "LEFT", "FORWARD", "FORWARD", "LEFT",
                "ENDREPEAT"
        });

        assertArrayEquals(new int[]{0, 0}, lb.getRobotPosition());
        assertArrayEquals(new String[]{
                "XOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOX",
                "...........................................",
                "XOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOX",
                "...........................................",
                "XOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOX",
                "...........................................",
                "XOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOX",
                "...........................................",
                "XOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOX",
                "...........................................",
                "XOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOX",
                "...........................................",
                "XOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOX",
                "...........................................",
        }, lb.getMap());
    }

    @Test
    public void test07() {
        LightBot lb = new LightBot(new String[]{
                "R.....O.",
                "........",
                "........",
                "........",
                "........",
                "........",
                "......O.",
        });

        lb.reset();
        lb.runProgram(new String[]{
                "FUNCTION F1",
                    "REPEAT 6", "FORWARD", "ENDREPEAT",
                "ENDFUNCTION",
                "CALL F1",
                "LIGHT",
                "RIGHT",
                "CALL F1",
                "LIGHT",
        });

        assertArrayEquals(new int[]{6,6}, lb.getRobotPosition());
        assertArrayEquals(new String[]{
                "......X.",
                "........",
                "........",
                "........",
                "........",
                "........",
                "......X.",
        }, lb.getMap());
    }

    @Test
    public void test08() {
        LightBot lb = new LightBot(new String[]{
                "R.....O.",
                "........",
                "........",
                "........",
                "........",
                "........",
                "......O.",
        });

        lb.reset();
        lb.runProgram(new String[]{
                "FUNCTION F1",
                "REPEAT 6", "FORWARD", "ENDREPEAT",
                "ENDFUNCTION",
                "CALL F1",
                "LIGHT",
                "RIGHT",
                "CALL F1",
                "LIGHT",
        });

        assertArrayEquals(new int[]{6, 6}, lb.getRobotPosition());
        assertArrayEquals(new String[]{
                "......X.",
                "........",
                "........",
                "........",
                "........",
                "........",
                "......X.",
        }, lb.getMap());
    }

    @Test
    public void test09() {
        LightBot lb = new LightBot(new String[]{
                ".......O....",
                ".......O....",
                ".......O....",
                ".....U.O....",
                ".......O....",
                ".......O....",
                ".......O....",
                ".......O....",
                ".......O....",
        });

        lb.reset();
        lb.runProgram(new String[]{
                "FUNCTION FW(N)",
                    "REPEAT N", "FORWARD", "ENDREPEAT",
                "ENDFUNCTION",
                "FUNCTION FWLG(M)",
                    "REPEAT M", "FORWARD", "FORWARD", "LIGHT", "ENDREPEAT",
                "ENDFUNCTION",
                "CALL FW(3)", "RIGHT",
                "CALL FW(2)", "RIGHT",
                "CALL FWLG(4)"

        });

        assertArrayEquals(new int[]{7,8}, lb.getRobotPosition());
        assertArrayEquals(new String[]{
                ".......O....",
                ".......O....",
                ".......X....",
                ".......O....",
                ".......X....",
                ".......O....",
                ".......X....",
                ".......O....",
                ".......X....",
        }, lb.getMap());

        lb.reset();
        lb.runProgram(new String[]{
                "CALL FW(3)", "RIGHT",
                "CALL FW(2)", "RIGHT",
                "CALL FWLG(4)",
                "FUNCTION FW(N)",
                "REPEAT N", "FORWARD", "ENDREPEAT",
                "ENDFUNCTION",
                "FUNCTION FWLG(M)",
                "REPEAT M", "FORWARD", "FORWARD", "LIGHT", "ENDREPEAT",
                "ENDFUNCTION",
        });

        assertArrayEquals(new int[]{7,8}, lb.getRobotPosition());
        assertArrayEquals(new String[]{
                ".......O....",
                ".......O....",
                ".......X....",
                ".......O....",
                ".......X....",
                ".......O....",
                ".......X....",
                ".......O....",
                ".......X....",
        }, lb.getMap());
    }

    @Test
    public void test10() {
        LightBot lb = new LightBot(new String[]{
                "R...........",
                "............",
                "............",
                "............",
                "............",
                "............",
                "............",
                "............",
                "............",
        });

        lb.reset();
        lb.runProgram(new String[]{
                "FUNCTION SQUARE",
                    "REPEAT 4",
                        "CALL LINE(4)",
                        "RIGHT",
                    "ENDREPEAT",
                "ENDFUNCTION",
                "FUNCTION LINE(LEN)",
                    "REPEAT LEN", "LIGHT", "FORWARD", "ENDREPEAT",
                "ENDFUNCTION",

                "CALL SQUARE"
        });

        assertArrayEquals(new int[]{0,0}, lb.getRobotPosition());
        assertArrayEquals(new String[]{
                "xxxxx.......",
                "x...x.......",
                "x...x.......",
                "x...x.......",
                "xxxxx.......",
                "............",
                "............",
                "............",
                "............",
        }, lb.getMap());
    }

    @Test
    public void test11() {
        LightBot lb = new LightBot(new String[]{
                "R...........",
                "............",
                "............",
                "............",
                "............",
                "............",
                "............",
                "............",
                "............",
        });

        lb.reset();
        lb.runProgram(new String[]{
                "FUNCTION F1",
                    "FORWARD", "CALL F2",
                "ENDFUNCTION",
                "FUNCTION F2",
                    "FORWARD", "CALL F3",
                "ENDFUNCTION",
                "FUNCTION F3",
                    "FORWARD", "CALL F4",
                "ENDFUNCTION",
                "FUNCTION F4",
                    "LIGHT",
                "ENDFUNCTION",

                "REPEAT 2", "CALL F1", "ENDREPEAT",
        });

        assertArrayEquals(new int[]{6,0}, lb.getRobotPosition());
        assertArrayEquals(new String[]{
                "...x..x.....",
                "............",
                "............",
                "............",
                "............",
                "............",
                "............",
                "............",
                "............",
        }, lb.getMap());
    }

    @Test
    public void test12() {
        LightBot lb = new LightBot(new String[]{
                "............",
                "............",
                "............",
                "............",
                "............",
                "............",
                "............",
                "U...........",
                "............",
        });

        lb.reset();
        lb.runProgram(new String[]{
                "FUNCTION LINE(N)",
                    "REPEAT N", "LIGHT", "FORWARD", "ENDREPEAT",
                "ENDFUNCTION",
                "FUNCTION SQUARE(N)",
                    "REPEAT 4", "CALL LINE(N)", "RIGHT", "ENDREPEAT",
                "ENDFUNCTION",

                "CALL SQUARE(5)",
        });

        assertArrayEquals(new int[]{0,7}, lb.getRobotPosition());
        assertArrayEquals(new String[]{
                "............",
                "............",
                "xxxxxx......",
                "x....x......",
                "x....x......",
                "x....x......",
                "x....x......",
                "xxxxxx......",
                "............",
        }, lb.getMap());
    }

}