package ecommerce.utils;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public abstract class BaseController {

    protected java.util.concurrent.Executor executor;

    public BaseController() {
    }

    public BaseController(java.util.concurrent.Executor executor) {
        this.executor = executor;
    }

    public <T> CompletableFuture<ApiReturn<T>> asyncResultOf(Supplier<T> supplier) {
        return CompletableFuture.supplyAsync(supplier, executor)
                .thenApply(result -> ApiReturn.success(result));
    }

    public <T> CompletableFuture<ApiReturn<T>> asyncResultOf(CompletableFuture<T> supplier) {
        return supplier.thenApplyAsync(result -> ApiReturn.success(result), executor);
    }

}
