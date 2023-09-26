package kr.co.company.vegan;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import java.util.List;

public class InfiniteSlideAdapter extends RecyclerView.Adapter<InfiniteSlideAdapter.SlideViewHolder> {
    private List<Integer> images;
    private ViewPager2 viewPager;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        }
    };

    public InfiniteSlideAdapter(Context context, List<Integer> images, ViewPager2 viewPager) {
        this.images = images;
        this.viewPager = viewPager;
    }

    @NonNull
    @Override
    public SlideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slide_item, parent, false);
        return new SlideViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SlideViewHolder holder, int position) {
        int imagePosition = position % images.size();
        holder.imageView.setImageResource(images.get(imagePosition));
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE; // 무한 슬라이딩을 위해 큰 값을 설정
    }

    public void startAutoSlider() {
        handler.postDelayed(runnable, 3000); // 3초마다 슬라이드 변경
    }

    public void stopAutoSlider() {
        handler.removeCallbacks(runnable);
    }

    class SlideViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public SlideViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
