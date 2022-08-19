package ru.diasoft.spring.booklibrarymvc.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "book_comment")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(
        name = "graph.bookcomment-book",
        attributeNodes = @NamedAttributeNode(value = "book", subgraph = "subgraph.book"),
        subgraphs = {
                @NamedSubgraph(name = "subgraph.book",
                        attributeNodes = {
                                @NamedAttributeNode(value = "author"),
                                @NamedAttributeNode(value = "genre")
                        })
        }
)
public class BookComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "text")
    private String text;

    @Override
    public String toString() {
        return "BookComment{" +
                "id=" + id +
                ", bookId=" + book.getId() +
                ", text='" + text + '\'' +
                '}';
    }
}
