package kr.co.company.vegan;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BoardListAdapter extends RecyclerView.Adapter<BoardListAdapter.BoardViewHolder> {
    private Context context;
    private ArrayList<Board> boardList;

    public BoardListAdapter(Context context) {
        this.context = context;
        this.boardList = new ArrayList<>();
        loadAllBoardsFromDatabase();
    }

    private void loadAllBoardsFromDatabase() {
        DatabaseReference boardsRef = FirebaseDatabase.getInstance().getReference("posts");

        boardsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boardList.clear(); // 기존 데이터를 지우고 새로 불러온 데이터로 갱신

                for (DataSnapshot boardSnapshot : dataSnapshot.getChildren()) {
                    Board board = boardSnapshot.getValue(Board.class);
                    if (board != null) {
                        // 게시판 객체에 ID를 설정
                        board.setId(boardSnapshot.getKey());
                        boardList.add(board);
                    }
                }

                notifyDataSetChanged(); // 어댑터에 데이터가 변경되었음을 알림
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 데이터 가져오기 실패 시 처리
                // 여기에 적절한 실패 처리를 추가할 수 있습니다.
            }
        });
    }

    public void addBoard(Board board) {
        // 새로운 게시판을 추가하고 RecyclerView 갱신
        boardList.add(board);
        notifyDataSetChanged();
    }

    public void clearBoards() {
        boardList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.board_list_item, parent, false);
        return new BoardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BoardViewHolder holder, int position) {
        final Board board = boardList.get(position);

        // 글 번호 표시
        if (holder.itemNumber != null) {
            holder.itemNumber.setText(String.valueOf(position + 1));
        }

        holder.boardTitle.setText(board.getTitle());

        // 게시판 제목 클릭 이벤트 처리
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 게시판 상세 화면으로 이동하는 인텐트
                Intent intent = new Intent(context, BoardDetailActivity.class);
                intent.putExtra("boardId", board.getId()); // 게시판 ID 전달
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return boardList.size();
    }

    public class BoardViewHolder extends RecyclerView.ViewHolder {
        // ViewHolder의 내부 클래스로서 게시판 아이템의 각 요소들을 정의
        TextView itemNumber;
        TextView boardTitle;

        public BoardViewHolder(final View itemView) {
            super(itemView);
            // itemNumber 초기화
            itemNumber = itemView.findViewById(R.id.itemNumber);
            boardTitle = itemView.findViewById(R.id.boardTitle);

            // 게시판 작성 버튼 설정
            Button regBtn = itemView.findViewById(R.id.reg_button);
            if (regBtn != null) {
                regBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 게시판 작성 버튼이 클릭되었을 때 수행할 동작 정의
                        // 새로운 화면으로 이동하는 인텐트를 사용하여 게시판 작성 화면으로 이동
                        Intent intent = new Intent(itemView.getContext(), BoardRegisterActivity.class);
                        itemView.getContext().startActivity(intent);
                    }
                });
            }
        }
    }
}



