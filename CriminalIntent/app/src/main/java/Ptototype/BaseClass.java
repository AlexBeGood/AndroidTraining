package Ptototype;

public class BaseClass implements IPrototype {

    private String mField;
    public BaseClass()
    {

    }

    public BaseClass(BaseClass prototype)
    {
        mField = prototype.mField;
    }

    @Override
    public IPrototype Clone() {
        return new BaseClass(this);
    }
}
