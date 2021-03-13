package ca.study.purpose;

public class CacheDemo {

    public static void main(String[] args) throws InterruptedException {
//        new CacheDemo().eternalCacheExample();
        new CacheDemo().lifeCacheExample();
    }

    private void eternalCacheExample() {
        int size = 5;
        MyCacheEngine<Integer, String> cache = new MyCacheEngineImpl<>(size, 0, 0, true);

        for (int i = 0; i < 10; i++) {
            cache.put(new MyCacheElement<>(i, "String: " + i));
        }

        for (int i = 0; i < 10; i++) {
            MyCacheElement<Integer, String> element = cache.get(i);
            System.out.println("String for " + i + ": " + (element != null ? element.getValue() : "null"));
        }

        System.out.println("Cache hits: " + cache.getHitCount());
        System.out.println("Cache misses: " + cache.getMissCount());

        cache.dispose();
    }

    private void lifeCacheExample() throws InterruptedException {
        int size = 5;
        MyCacheEngine<Integer, String> cache = new MyCacheEngineImpl<>(size, 1000, 0, false);

        for (int i = 0; i < size; i++) {
            cache.put(new MyCacheElement<>(i, "String: " + i));
        }

        for (int i = 0; i < size; i++) {
            MyCacheElement<Integer, String> element = cache.get(i);
            System.out.println("String for " + i + ": " + (element != null ? element.getValue() : "null"));
        }

        System.out.println("Cache hits: " + cache.getHitCount());
        System.out.println("Cache misses: " + cache.getMissCount());

        Thread.sleep(1000);

        for (int i = 0; i < size; i++) {
            MyCacheElement<Integer, String> element = cache.get(i);
            System.out.println("String for " + i + ": " + (element != null ? element.getValue() : "null"));
        }

        System.out.println("Cache hits: " + cache.getHitCount());
        System.out.println("Cache misses: " + cache.getMissCount());

        cache.dispose();
    }

}