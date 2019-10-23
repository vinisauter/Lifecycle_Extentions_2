package androidx.lifecycle;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A lifecycle-aware observable that sends only new updates after subscription, used for events like
 * navigation and Snackbar messages.
 * <p>
 * This avoids a common problem with events: on configuration change (like rotation) an update
 * can be emitted if the observer is active. This LiveData only calls the observable if there's an
 * explicit call to setValue() or call().
 * <p>
 * Note that only one observer is going to be notified of changes.
 *
 * @see <a href="https://github.com/googlesamples/android-architecture/tree/dev-todo-mvvm-live#live-events">Live events</a>
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class SingleLiveEvent<T> extends MediatorLiveData<T> {

    private static final String TAG = "SingleLiveEvent";

    private final AtomicBoolean mPending = new AtomicBoolean(false);

    public SingleLiveEvent() {
        super();
    }

    public SingleLiveEvent(@NonNull LiveData<T> source) {
        super();
        super.addSource(source, new Observer<T>() {
            @Override
            public void onChanged(T t) {
                SingleLiveEvent.this.setValue(t);
            }
        });
    }

    public <S> SingleLiveEvent(@NonNull LiveData<S> source, @NonNull final ReferenceFunction<S, SingleLiveEvent<T>> mapFunction) {
        super();
        this.addSource(source, mapFunction);
    }

    public <S> void addSource(@NonNull LiveData<S> source, @NonNull final ReferenceFunction<S, SingleLiveEvent<T>> mapFunction) {
        super.addSource(source, new Observer<S>() {
            @Override
            public void onChanged(S input) {
                mapFunction.apply(input, SingleLiveEvent.this);
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
                if (mPending.compareAndSet(true, false)) {
                    observer.onChanged(t);
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
                if (mPending.compareAndSet(true, false)) {
                    observer.onChanged(t);
                }
            }
        });
    }

    @MainThread
    public void setValue(@Nullable T t) {
        mPending.set(true);
        super.setValue(t);
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    public void call() {
        this.setValue(null);
    }
}