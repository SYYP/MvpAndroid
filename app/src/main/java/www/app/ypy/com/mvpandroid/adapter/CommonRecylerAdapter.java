package www.app.ypy.com.mvpandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class CommonRecylerAdapter<T> extends RecyclerView.Adapter<CommonRecylerHolder> {
    protected Context context;
    private List<T> list;
    private LayoutInflater inflater;
    private int itemLayoutId;
    private boolean isScrolling;
    private OnItemClickListener listener;
    private OnItemLongClickListener longClickListener;
    private RecyclerView recyclerView;

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    public void insert(T item, int position) {
        list.add(position, item);
        notifyItemInserted(position);
    }

    public void add(T item ) {
        list.add(  item);
      notifyDataSetChanged();
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void delete(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public  void deletAll()
    {
        list.clear();

        notifyDataSetChanged();
    }

    public CommonRecylerAdapter(Context context, List<T> list, int itemLayoutId) {
        this.context = context;
        this.list = list;
        this.itemLayoutId = itemLayoutId;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public CommonRecylerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(itemLayoutId, parent, false);
        return CommonRecylerHolder.getRecylerHolder(context, view);
    }

    @Override
    public void onBindViewHolder(CommonRecylerHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null )
//                    int position = recyclerView.getChildAdapterPosition(view);
                    listener.onItemClick(recyclerView, view, position);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {

//            private int mPositoin;

            @Override
            public boolean onLongClick(View view) {
                if (longClickListener != null ) {
                    longClickListener.onItemLongClick(recyclerView, view, position);
                    return true;
                }
                return false;
            }
        });

        convert(holder,list.get(position),position,isScrolling);
    }

    public T get(int pos)
    {
        return  list.get(pos);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    public abstract void convert(CommonRecylerHolder holder, T item, int position, boolean isScrolling);

    @FunctionalInterface
    public interface OnItemClickListener {
        void onItemClick(RecyclerView parent, View view, int position);
    }
    @FunctionalInterface
    public interface OnItemLongClickListener {
        boolean onItemLongClick(RecyclerView parent, View view, int position);
    }
}
