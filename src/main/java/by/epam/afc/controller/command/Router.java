package by.epam.afc.controller.command;

/**
 * The type Router.
 */
public class Router {
    private final DispatchType dispatchType;
    private final String targetPath;

    /**
     * Instantiates a new Router.
     *
     * @param routeType  the route dispatch type
     * @param targetPath the route target path
     */
    public Router(DispatchType routeType, String targetPath) {
        this.dispatchType = routeType;
        this.targetPath = targetPath;
    }

    /**
     * Gets dispatch type.
     *
     * @return the dispatch type
     */
    public DispatchType getDispatchType() {
        return dispatchType;
    }

    /**
     * Gets target path.
     *
     * @return the target path
     */
    public String getTargetPath() {
        return targetPath;
    }

    /**
     * The enum Dispatch type.
     */
    public enum DispatchType {
        /**
         * Forward dispatch type.
         */
        FORWARD,
        /**
         * Redirect dispatch type.
         */
        REDIRECT
    }

    /**
     * Equals boolean.
     *
     * @param o the o
     * @return the boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Router router = (Router) o;

        if (dispatchType != router.dispatchType) return false;
        return targetPath.equals(router.targetPath);
    }

    /**
     * Hash code int.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        int result = dispatchType.hashCode();
        result = 31 * result + targetPath.hashCode();
        return result;
    }

    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "Router{" +
                "routeType=" + dispatchType +
                ", targetPath='" + targetPath + '\'' +
                '}';
    }
}
