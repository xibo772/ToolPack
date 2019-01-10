package android.biginner.com.tool_aar.baseMore.recycler;

/**
 * recyclerView 多布局的基础model类
 * <p>
 * 实现接口 ，定义自己单独属性,setter/getter
 * <p>
 * 其实此接口可以写作 父类class，可以直接在父类中，
 */
public interface BaseValue {
    /**
     * 设置 布局，
     * 处理方法相同，
     * <p>
     * 可以直接交给 工厂类 BaseFactory 进行处理（推荐）：
     * return factory.type(this);
     * <p>
     * 也可以直接在子类中进行处理(不建议这样写)
     * return R.layout.layout_1；
     */
    int getLayoutId(BaseFactory factory);

    /**
     * 设置 Item 的跨度，
     * <p>
     * 可以直接交给 工厂类 BaseFactory 进行处理（推荐）：
     * return factory.spanCount(this);
     * <p>
     * 也可以直接在子类中进行处理（不建议这样写）：
     * return 5；
     */
    int getSpanCount(BaseFactory factory);

}
////////////// 示例 👇 //////////////
    /*
    public class Bean_1 implements BaseValue {
    private String name;

    public MineBean(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getLayoutId(BaseFactory factory) {
        return factory.type(this);
    }

    @Override
    public int getSpanCount(BaseFactory factory) {
        return factory.spanCount(this);
    }
}
     */
////////////// Bean 示例 👆 //////////////
