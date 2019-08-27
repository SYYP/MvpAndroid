package www.app.ypy.com.mvpandroid.network;

import android.util.Pair;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func0;
import rx.functions.Func1;
import www.app.ypy.com.mvpandroid.dialog.LoadingProgressDialog;

/**
 * User: Axl_Jacobs(Axl.Jacobs@gmail.com)
 * Date: 2016-09-01
 * Time: 20:27
 * FIXME
 * Rx处理服务器返回
 */
public class RxResultHelper {

    public static <T> Observable.Transformer<BaseResult<T>, T> handleResult(final LoadingProgressDialog loadingProgressDialog) {
        return tObservable -> tObservable.flatMap(
                new Func1<BaseResult<T>, Observable<? extends T>>() {
                    @Override
                    public Observable<? extends T> call(BaseResult<T> entity) {
                        if (loadingProgressDialog != null && loadingProgressDialog.isShowing()) {
                            loadingProgressDialog.dismiss();
                        }
                        if (entity.getCode() == 100000) {
                            return createData(entity.getData());
                        } else {
                            return Observable.error(new ApiException(entity.getCode() + "", entity.getMsg(), entity.getData()));
                        }
                    }
                },
                new Func1<Throwable, Observable<? extends T>>() {
                    @Override
                    public Observable<? extends T> call(Throwable throwable) {
                        if (loadingProgressDialog != null && loadingProgressDialog.isShowing()) {
                            loadingProgressDialog.dismiss();
                        }
                        return Observable.error(throwable);
                    }
                }, new Func0<Observable<? extends T>>() {
                    @Override
                    public Observable<? extends T> call() {
                        return null;
                    }
                }
        );
    }

    public static <T> Observable.Transformer<BaseResult<T>, Pair<Integer, T>> handleResult_new(final LoadingProgressDialog loadingProgressDialog) {
        return new Observable.Transformer<BaseResult<T>, Pair<Integer, T>>() {
            @Override
            public Observable<Pair<Integer, T>> call(Observable<BaseResult<T>> tObservable) {
                return tObservable.flatMap(
                        new Func1<BaseResult<T>, Observable<Pair<Integer, T>>>() {
                            @Override
                            public Observable<Pair<Integer, T>> call(BaseResult<T> entity) {
                                if (loadingProgressDialog != null) {
                                    loadingProgressDialog.dismiss();
                                }
                                if (entity.getCode() == 200) {
                                    Pair<Integer, T> pair = new Pair<Integer, T>(entity.getCount(), entity.getData());
                                    return createData(pair);
                                } else {
                                    return Observable.error(new ApiException(entity.getCode() + "", entity.getMsg(), entity.getData()));
                                }
                            }
                        },
                        new Func1<Throwable, Observable<Pair<Integer, T>>>() {
                            @Override
                            public Observable<Pair<Integer, T>> call(Throwable throwable) {
                                if (loadingProgressDialog != null) {
                                    loadingProgressDialog.dismiss();
                                }
                                return Observable.error(throwable);
                            }
                        }, new Func0<Observable<Pair<Integer, T>>>() {
                            @Override
                            public Observable<Pair<Integer, T>> call() {
                                return null;
                            }
                        }
                );
            }
        };
    }

    public static <T> Observable.Transformer<BaseResult<T>, BaseResult<T>> dismissDialog(final LoadingProgressDialog loadingProgressDialog) {
        return new Observable.Transformer<BaseResult<T>, BaseResult<T>>() {
            @Override
            public Observable<BaseResult<T>> call(Observable<BaseResult<T>> tObservable) {
                return tObservable.flatMap(
                        new Func1<BaseResult<T>, Observable<BaseResult<T>>>() {
                            @Override
                            public Observable<BaseResult<T>> call(BaseResult<T> entity) {
                                if (loadingProgressDialog != null && loadingProgressDialog.isShowing()) {
                                    loadingProgressDialog.dismiss();
                                }
                                if (entity.getCode() == 200) {
                                    return createData(entity);
                                } else {
                                    return Observable.error(new ApiException(entity.getCode() + "", entity.getMsg(), entity.getData()));
                                }
                            }
                        },
                        new Func1<Throwable, Observable<BaseResult<T>>>() {
                            @Override
                            public Observable<BaseResult<T>> call(Throwable throwable) {
                                if (loadingProgressDialog != null && loadingProgressDialog.isShowing()) {
                                    loadingProgressDialog.dismiss();
                                }
                                return Observable.error(throwable);
                            }
                        }, new Func0<Observable<BaseResult<T>>>() {
                            @Override
                            public Observable<BaseResult<T>> call() {
                                return null;
                            }
                        }
                );
            }
        };
    }

    public static class ResultHandler<T> implements Func1<BaseResult<T>, Observable<T>> {
        @Override
        public Observable<T> call(BaseResult<T> tBaseResult) {
            if (tBaseResult.getCode() == 200) {
                return createData(tBaseResult.getData());
            } else {
                return Observable.error(new ApiException(tBaseResult.getCode() + "", tBaseResult.getMsg(), tBaseResult.getData()));
            }
        }
    }

    private static <T> Observable<T> createData(T t) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(t);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }


}