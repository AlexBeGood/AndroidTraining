package Ptototype;

public class NestedBaseClass extends BaseClass {

    private int mField;
    public NestedBaseClass()
    {

    }

    public NestedBaseClass(NestedBaseClass prototype){
        super(prototype);
        mField = prototype.mField;
    }

    @Override
    public IPrototype Clone()
    {
        return new NestedBaseClass(this);
    }
}
