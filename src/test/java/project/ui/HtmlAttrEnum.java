package project.ui;

/**
 * \brief The class provides html attributes name. This helps to avoid a spelling mistakes in attribute name
 */
public enum HtmlAttrEnum {
    ARIA_HIDDEN("aria-hidden"),
    CLASS("class"),
    ID("id");

    HtmlAttrEnum(String name) {
        this.name = name;
    }

    String name;

    @Override
    public String toString() {
        return name;
    }
}
