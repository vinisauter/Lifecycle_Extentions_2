
package androidx.lifecycle;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;

/**
 * A lifecycle-aware observable that sends only new updates after subscription, used for not null values.
 * <p>
 * This avoids a common problem with events: on configuration change (like rotation) an update
 * can be emitted if the observer is active. This LiveData only calls the observable if there's an
 * explicit call to setValue() or postValuew().
 * <p>
 * Note that only one observer is going to be notified of changes.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class SingleLiveData<T> extends MediatorLiveData<T> {

    public SingleLiveData() {
        super();
    }

    public SingleLiveData(@NonNull LiveData<T> source) {
        super();
        super.addSource(source, new Observer<T>() {
            @Override
            public void onChanged(T t) {
                SingleLiveData.this.setValue(t);
            }
        });
    }

    public <S> SingleLiveData(@NonNull LiveData<S> source, @NonNull final ReferenceFunction<S, SingleLiveData<T>> mapFunction) {
        super();
        this.addSource(source, mapFunction);
    }

    public <S> void addSource(@NonNull LiveData<S> source, @NonNull final ReferenceFunction<S, SingleLiveData<T>> mapFunction) {
        super.addSource(source, new Observer<S>() {
            @Override
            public void onChanged(S input) {
                mapFunction.apply(input, SingleLiveData.this);
            }
        });
    }

    @MainThread
    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull final Observer<? super T> observer) {
        // Observe the internal LiveData
        super.observe(owner, new Observer<T>() {
            @Override
            public void onChanged(T t) {
                // Multiple observers registered but only one will be notified of changes.
                if (t != null) {
                    observer.onChanged(t);
                    SingleLiveData.super.setValue(null);
                }
            }
        });
    }

    @Override
    public void observeForever(@NonNull final Observer<? super T> observer) {
        // Observe the internal LiveData
        super.observeForever(new Observer<T>() {
            @Override
            public void onChanged(T t) {
                // Multiple observers registered but only one will be notified of changes.
                if (t != null) {
                    observer.onChanged(t);
                    SingleLiveData.super.setValue(null);
                }
            }
        });
    }
}