package com.SistemaReparações.Menu;

public interface IMenu {
    public void run();
    public void option(String name, Menu.PreCondition p, Menu.Handler h);
    public void runOnce();
    public void setPreCondition(int i, Menu.PreCondition b);
    public void setHandler(int i, Menu.Handler h);

}
