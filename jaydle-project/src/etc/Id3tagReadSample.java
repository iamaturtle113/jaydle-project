package etc;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
 
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
 
import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;
import org.farng.mp3.id3.AbstractID3v2;
import org.farng.mp3.id3.ID3v1;
 
// MP3ファイルのID3タグを読み込んで表示するサンプル
// Java ID3 Tag Library + 文字化け対応
public class Id3tagReadSample extends JFrame {
 
  public static void main(String[] args) {
    new Id3tagReadSample();
  }
 
  JTextField fileName = new JTextField(16);
  JTextField title = new JTextField(16);
  JTextField albumTitle = new JTextField(16);
  JTextField track = new JTextField(16);
  JTextField artist = new JTextField(16);
 
  public Id3tagReadSample() {
    setTitle("AutoID3TaggerProto 文字化け対応");
    setBounds(100, 200, 1000, 300);//x,y, width,height
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLayout(new FlowLayout());
    // ドロップターゲット設定
    new DropTarget(this, DnDConstants.ACTION_COPY, new MyDropTargetListener());
 
    // 編集しない
    fileName.setEditable(false);
    title.setEditable(false);
    albumTitle.setEditable(false);
    track.setEditable(false);
    artist.setEditable(false);
 
    // 画面要素貼り付け
    add(new JLabel("Drop mp3 files"));
    putHr();
    putLabel("File name:");
    add(fileName);
    putHr();
    putLabel("Artist:");
    add(artist);
    putHr();
    putLabel("Title:");
    add(title);
    putHr();
    putLabel("Album:");
    add(albumTitle);
    putHr();
    putLabel("Track:");
    add(track);
    putHr();
 
    // 画面表示
    setVisible(true);
  }
 
  // mp3ファイルからID3タグを読んで表示します
  private void readTag(File file) {
    MP3File mp3file;
    try {
      mp3file = new MP3File(file);
 
      String fileName = file.getName();
      String title = "--";
      String albumTitle = "--";
      String track = "--";
      String artist = "--";
      // v2タグを優先的に表示
      if (mp3file.hasID3v2Tag()) {
        AbstractID3v2 v2 = mp3file.getID3v2Tag();
        title = v2.getSongTitle();
        albumTitle = v2.getAlbumTitle();
        track = v2.getTrackNumberOnAlbum();
        artist = v2.getLeadArtist();
 
      } else if (mp3file.hasID3v2Tag()) {
        // v2タグがない場合v1タグを表示
        ID3v1 v1 = mp3file.getID3v1Tag();
 
        byte[] ary = v1.getTitle().getBytes("ISO-8859-1");
        title = new String(ary);
        ary = v1.getAlbumTitle().getBytes("ISO-8859-1");
        albumTitle = new String(ary);
        ary = v1.getTrackNumberOnAlbum().getBytes("ISO-8859-1");
        track = new String(ary);
        ary = v1.getArtist().getBytes("ISO-8859-1");
        artist = new String(ary);
      }
 
      // 読み込んだタグを画面に設定
      this.fileName.setText(fileName);
      this.title.setText(title);
      this.artist.setText(artist);
      this.albumTitle.setText(albumTitle);
      this.track.setText(track);
 
    } catch (IOException e) {
      e.printStackTrace();
    } catch (TagException e) {
      e.printStackTrace();
    }
  }
 
  // 以下画面設定、DnD用
  // ラベル
  public void putLabel(String text) {
    JLabel l = new JLabel(text);
    Dimension dim = l.getPreferredSize();
    dim.setSize(85, dim.height);
    l.setPreferredSize(dim);
    l.setHorizontalAlignment(JLabel.RIGHT);
    add(l);
  }
 
  // 水平線
  public void putHr() {
    putHr(1000, 0);
  }
 
  public void putHr(int width, int hight) {
    JSeparator sp = new JSeparator(JSeparator.HORIZONTAL);
    sp.setPreferredSize(new Dimension(width, hight));
    add(sp);
  }
 
  // ドロップターゲットリスナー
  // ドロップされたファイルを受け取り、最初のファイルだけ処理します
  class MyDropTargetListener extends DropTargetAdapter {
    @Override
    public void drop(DropTargetDropEvent dtde) {
      dtde.acceptDrop(DnDConstants.ACTION_COPY);
      boolean b = false;
      try {
        if (dtde.getTransferable().isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
          b = true;
          List<File> list = (List<File>) dtde.getTransferable().getTransferData(
              DataFlavor.javaFileListFlavor);
          // 最初のファイルだけ取得
          File file = list.get(0);
          readTag(file);
        }
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        dtde.dropComplete(b);
      }
    }
  }
}