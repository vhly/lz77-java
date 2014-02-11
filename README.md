lz77-java
=========

LZ77 implement for Java, for PalmDoc content de/compress

Usage:

    1. Read compressed content

        InputStream in = .......; // For your resource
        LZ77InputStream inputStream = new LZ77InputStream(in);
        while (true) {
            int d = inputStream.read();
            if (d == -1) {
                break;
            }
            System.out.println("d = " + (char) d);
        }
        inputStream.close();

        or You can use byte[] StreamUtil.readStream(InputStream stream); for read fully.


    2. Compress content. Use LZ77OutputStream,
       When you want get compress data, please invoke LZ77OutputStream.flush() first.

       ByteArrayOutputStream bout = ......; // For your output stream.
       LZ77OutputStream lz77OutputStream = new LZ77OutputStream(bout);
       try {
            lz77OutputStream.write(data);
            lz77OutputStream.flush();
            byte[] encData = bout.toByteArray();
            int origLen = data.length;
            int encLen = encData.length;
            float dol = origLen;
            float eol = encLen;
            float rate = eol / dol;
            System.out.println("rate = " + rate);
       } catch (IOException e) {
            e.printStackTrace();
       } finally {
            StreamUtil.close(lz77OutputStream);
            StreamUtil.close(bout);
       }

    3. Important!

        1) LZ77OutputStream implement can't support Unicode content compress now,
            I will research this issue. It will come soon.

Verify Tag support:

    1. Import GPG public key

        -----BEGIN PGP PUBLIC KEY BLOCK-----
        Version: GnuPG/MacGPG2 v2.0.22 (Darwin)
        Comment: GPGTools - http://gpgtools.org

        mQENBE/i89kBCADz5fxOOSRJnwyBcL2G0e1g1G0iipMtV3yeFw2w6WZdyB2UJ9KR
        XKkzCQ63Yg8eBfq7XVEQ+2nzG7goUOULivOBWJWpj3+v7NlstoL45sDhA9p/Npvv
        amHpNVPUSQmP8eUWhis5PBEV5IwHBs0QSA3UudgmUrTFUWtGFaZzZx3Nn5FWK8hy
        SC52NvFiB83/y/oKRSVKfDjQGPXB5I2vjlvGWA3kaI/OUSHMVn41kWcsxqI9FfY6
        CC6ZI9pfm/itYFjF26zG8b10OsR9CnF+js1C7/5jcf72NceNmpimb3LNjAdcQkhO
        cgh4N18LYYf8e6579rl1rukPcWodqfy9S15bABEBAAG0NVpoYW5nIFhpYW9oZSAo
        WmhhbmcgWGlhb2hlIHZobHkgZW1haWwpIDx2aGx5QDE2My5jb20+iQE4BBMBAgAi
        BQJP4vPZAhsPBgsJCAcDAgYVCAIJCgsEFgIDAQIeAQIXgAAKCRBmDzr4Y8AqiuIS
        B/9GBHq8kVLctUNrFKKgLNn5Jvn45HAwlO1arAI6nEME4EznjZKEnrnYcpW8q/A6
        7wrGODi+F8+VdtPQ+ak+5CaHbSeP0EO7QvQe4tZ224YJhuUm6w+BAs9pvaBzUdGQ
        C+9AH+kh0q+iLMaaEJrKZ0972kQLYajTAxGZ5T23SN37t/Yde1OmrdIgUScFcAZK
        QA/Te6p2cA/on9q88+YDHbBQNbL95/YPxtif1r5IIu/Zfi4w0/NGAXeG1q1JpoF5
        1tNDR3OXhtDvviKK5YWkFMD2Os8g8rilqHWpp3m0P1jlWSeWrh2t7FxZSEmJlVqK
        q4GE3/CJoRX4wIv6fLSsiEQF0f8AAFIm/wAAUiEBEAABAQAAAAAAAAAAAAAAAP/Y
        /+AAEEpGSUYAAQEBAEgASAAA/+IFWElDQ19QUk9GSUxFAAEBAAAFSGFwcGwCIAAA
        c2NuclJHQiBYWVogB9MABwABAAAAAAAAYWNzcEFQUEwAAAAAYXBwbAAAAAAAAAAA
        AAAAAAAAAAAAAPbWAAEAAAAA0y1hcHBsAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
        AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAALclhZWgAAAQgAAAAUZ1hZWgAAARwAAAAU
        YlhZWgAAATAAAAAUd3RwdAAAAUQAAAAUY2hhZAAAAVgAAAAsclRSQwAAAYQAAAAO
        Z1RSQwAAAYQAAAAOYlRSQwAAAYQAAAAOZGVzYwAABNgAAABuY3BydAAABJQAAABB
        ZHNjbQAAAZQAAAL+WFlaIAAAAAAAAHRLAAA+HQAAA8tYWVogAAAAAAAAWnMAAKym
        AAAXJlhZWiAAAAAAAAAoGAAAFVcAALgzWFlaIAAAAAAAAPNSAAEAAAABFs9zZjMy
        AAAAAAABDEIAAAXe///zJgAAB5IAAP2R///7ov///aMAAAPcAADAbGN1cnYAAAAA
        AAAAAQIzAABtbHVjAAAAAAAAAA8AAAAMZW5VUwAAACQAAAKeZXNFUwAAACwAAAFM
        ZGFESwAAADQAAAHaZGVERQAAACwAAAGYZmlGSQAAACgAAADEZnJGVQAAADwAAALC
        aXRJVAAAACwAAAJybmxOTAAAACQAAAIObm9OTwAAACAAAAF4cHRCUgAAACgAAAJK
        c3ZTRQAAACoAAADsamFKUAAAABwAAAEWa29LUgAAABgAAAIyemhUVwAAABoAAAEy
        emhDTgAAABYAAAHEAEsAYQBtAGUAcgBhAG4AIABSAEcAQgAtAHAAcgBvAGYAaQBp
        AGwAaQBSAEcAQgAtAHAAcgBvAGYAaQBsACAAZgD2AHIAIABLAGEAbQBlAHIAYTCr
        MOEw6QAgAFIARwBCACAw1zDtMNUwoTCkMOtleE9NdvhqXwAgAFIARwBCACCCcl9p
        Y8+P8ABQAGUAcgBmAGkAbAAgAFIARwBCACAAcABhAHIAYQAgAEMA4QBtAGEAcgBh
        AFIARwBCAC0AawBhAG0AZQByAGEAcAByAG8AZgBpAGwAUgBHAEIALQBQAHIAbwBm
        AGkAbAAgAGYA/AByACAASwBhAG0AZQByAGEAc3b4ZzoAIABSAEcAQgAgY8+P8GWH
        TvYAUgBHAEIALQBiAGUAcwBrAHIAaQB2AGUAbABzAGUAIAB0AGkAbAAgAEsAYQBt
        AGUAcgBhAFIARwBCAC0AcAByAG8AZgBpAGUAbAAgAEMAYQBtAGUAcgBhznS6VLd8
        ACAAUgBHAEIAINUEuFzTDMd8AFAAZQByAGYAaQBsACAAUgBHAEIAIABkAGUAIABD
        AOIAbQBlAHIAYQBQAHIAbwBmAGkAbABvACAAUgBHAEIAIABGAG8AdABvAGMAYQBt
        AGUAcgBhAEMAYQBtAGUAcgBhACAAUgBHAEIAIABQAHIAbwBmAGkAbABlAFAAcgBv
        AGYAaQBsACAAUgBWAEIAIABkAGUAIABsIBkAYQBwAHAAYQByAGUAaQBsAC0AcABo
        AG8AdABvAAB0ZXh0AAAAAENvcHlyaWdodCAyMDAzIEFwcGxlIENvbXB1dGVyIElu
        Yy4sIGFsbCByaWdodHMgcmVzZXJ2ZWQuAAAAAGRlc2MAAAAAAAAAE0NhbWVyYSBS
        R0IgUHJvZmlsZQAAAAAAAAAAAAAAE0NhbWVyYSBSR0IgUHJvZmlsZQAAAAAAAAAA
        AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAD/4QCA
        RXhpZgAATU0AKgAAAAgABQESAAMAAAABAAEAAAEaAAUAAAABAAAASgEbAAUAAAAB
        AAAAUgEoAAMAAAABAAIAAIdpAAQAAAABAAAAWgAAAAAAAABIAAAAAQAAAEgAAAAB
        AAKgAgAEAAAAAQAAAF+gAwAEAAAAAQAAAIAAAAAA/9sAQwABAQEBAQEBAQEBAQEB
        AQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEB
        AQEB/9sAQwEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEB
        AQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEB/8AAEQgAgABfAwERAAIRAQMRAf/EAB8A
        AAEFAQEBAQEBAAAAAAAAAAABAgMEBQYHCAkKC//EALUQAAIBAwMCBAMFBQQEAAAB
        fQECAwAEEQUSITFBBhNRYQcicRQygZGhCCNCscEVUtHwJDNicoIJChYXGBkaJSYn
        KCkqNDU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6g4SFhoeI
        iYqSk5SVlpeYmZqio6Slpqeoqaqys7S1tre4ubrCw8TFxsfIycrS09TV1tfY2drh
        4uPk5ebn6Onq8fLz9PX29/j5+v/EAB8BAAMBAQEBAQEBAQEAAAAAAAABAgMEBQYH
        CAkKC//EALURAAIBAgQEAwQHBQQEAAECdwABAgMRBAUhMQYSQVEHYXETIjKBCBRC
        kaGxwQkjM1LwFWJy0QoWJDThJfEXGBkaJicoKSo1Njc4OTpDREVGR0hJSlNUVVZX
        WFlaY2RlZmdoaWpzdHV2d3h5eoKDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKz
        tLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uLj5OXm5+jp6vLz9PX29/j5+v/aAAwD
        AQACEQMRAD8A/tvWMH6svU+mM8Z6+o9DXLzO1ndp2Xxb23SXq/cV9UnoeW3e6Tl7
        0kpXStG2ui3lond7rTqW44PlDbck8gdzjt7Z5A7Z5PWpk7WUb3d3bRpK7fKrPu9e
        zXdDhdS/md7uWt/7u+z1tpvfvcZ9s020YveTJbo3BlmYeWpb5dsj8+SScL+9C88Z
        xycpzSjFt3jHRPbfR+8tLpvTXu2bxW71s3rfa/8ALZa7vRu9k9e56/4dvLK8023k
        srm3uYflXzLeVJULAYIyhIVgchkbDBhyOa7KNuXR31t80lf1ff7uhsmmoJd9fXT9
        Gfz5ftJ/Gjw18OLvxfe6hq0cd5Z6jrchsoYpbuUTf2jcJCk4t1cRPO5VY4XbzX3Z
        RK8it7WdXkope9Wqv3nyuU20nby68z93XWzKoUlU9+bcpRkoy3vbnaXLr7zkparV
        x095I/n+179uLw/oPxQ8VeNfG3wz8XfEKBrx7hbLQ9R0rSpdT+wxRRWOimXVr+zh
        0TT5XjaLWbk3Bv4rLzTp9tLdzAD6SlTx9fLMDhuRUY0adbmnKLjCpKdVylOn7NOU
        p8vKo1HGV1tpqZQy3DRx+OrKveeJnRny4hucKdOEfZvD8yTavLmqTgklqeCfGb/g
        tV+2p48vddtPDzeA/hf4c1qys/Do8L+FPB7G30bwbo1qbLSvB/h/Wr3VIJbLRobM
        uL6S1sI7u/upru7Etu0+I+KeQYLEzVbEYitWq01WjQbnGlySUlUfJQ5Zvl0fxTvU
        krt9D3YV/q9BOFOnKjWqVYuMnd1akYQppScVeMN5LVqKdved2/zb1v8Aau8b654n
        0jX/ABf4f8OXTaTY3tisXhuFdG8yynuLeZla1E1zEP31rBjErMR5pO5mBr3KFKWE
        y+VJ16841J06tFYm69m3JpKbhpaM3J3imkklq9X5uLwkK9ZqnalVi7xdJKcZ02rL
        n5VF+0clFNw962jvbX6R+HXxD0PxPofh+x0axu/tgU3LGe1lAudW1G7casYnRXtc
        Zu2gDyMPLXL4CqXX4DJsq4to8S4/M+IeIK1LAVsxxn9n5Lh6lDE4aOG5ZQwlOdV0
        3WhS5YyxMqcZe0dZqHPGMbP18w+pfUcPh8ty2k8RRwVOnicbVcnN4hJLETirxUqs
        pWhKpJKMVJtJu9vsD4UXGg/C7452Go66dKku9Ht9DvI0a2N7FFLrPg/V7fFvp9wu
        y81SK/e1ijvFDQW8luzjIOR9ZmSq4vLlUhzzvTVKNRpxnNwxam42UvhjDVt2nzNW
        k00l4uFhSoVHKVnKnShzuatUiqsHf2cbN1IOUeXmXvOMW9E7Hb/tR+I7zxF4bmMU
        P2PQHit9TsrWdEbUJrwanbBri9ndfNaOEM5soztSNZCFTZFEw5ctp8kn7S8p1ajh
        O3tbxk7uTpLpOy9+6a0lbWRzY/ExcYSdNQpwlUbrWipJRptW5JpwlJqWjd+V8vWz
        MbVIifidrOASJPCPhaUZ7nZdKxLZ+Y5A5PTtndXrKXJThaNR2u/aKOvNonFate9G
        SnKLd01c86lKo5uTinGM7qU3KPtOen7zm7tyjG0VHliqak93Lb++5bf7p4B2AZ44
        x269++fWvFtrtq9rO0t3bVaN7Po9O6Oj4UndJPZLeP8ANrrd6202vr5cD8RPir4K
        +FOjtqvivUktnKOLbT96peXUgG7CKwfEJXJa4CyIg52FsKYnGTUklzNq7StHmf2r
        Sdrcqemur0967RWu0bt6O2snZ730u9N0rWd3e5+Lvx5/4KSfF+9vtY0r4FeBd8lh
        ciC7vbPRJ/EESwNIRDLda1qctnofnuu2RtJhD38ceDcpAkqqM4whOooVKsKSlFSS
        960v7rUouzlG75kmnK3Q9GlQl7KUrLVSlFwlvHZvW0naW+ra/L43l/4K3/tBfAl9
        R8Q+J/DUsNnKk9r4pl0CyhEJSSSIvfwaChuLeC/0aGWa7OoWqR+dMpsZmlgKuvfg
        sHzSlavGUueUYQk2lJbxT1vzRvrayqPSLWwq1GUVGVOd26d6q5b1YJpO9opyqPdK
        MYuSXvJO7T/KL9oz9s7RPGOj21/a+JPEOt2l4us6q2i6TqEFjqF5rWv3r3j6z4o1
        CdQkF/pkdwLVbK3aV45HZYuACvr0MBToyjXqx9rP2kJcj5Ycz57P2cZNc0eZXcft
        LSWiucbVasqcaCqUJxlKnFNTfOkr8zmlePnU68zTPxy8YeJLnxPqGrXegX2qvqMB
        N19g1TUHuHm5xM9lJuhik2A48mWLLAli7M2a9WcVXSlUp1oKU3FKSalGK5mrcvuq
        HLy2irNQ9520S6Uoxp/VryqSipwkqS51JxcJV3OE1Kq+Zu8ajlrblTSufNt94u16
        OeWH7NM0ZYxy29zcsUEgJB8vJPlSA5C7CMHKk4wafsI3UpShzuV3GMY2jtGVPnau
        oq3M5PV3t3vtRrVJwcISXsvaKNmo04aQi+VKzi5XslC6k5Jty0aOOm126imMklrc
        RHrjz/PXLvnzEbdjAAw6jBA6DPBuVGnytU6lOMYuUHGajGpKlZS9nSmm4ttttU/i
        bcXe6d5lWqwq05Qip88HCcq0VzxlOUVKT+wpR920lzNQV7Nb+yfCf9ovxh8HteGr
        6HHb6ppc4ddU0K/85NN1WGaMwyj7RDFNNpd5sJVL62hkYdJY2HzpxYvAUas+arFw
        XtVOm370oySXI5W0q33lFVI6pRtzNms8RNRnD2k5UeWdOpXipScrySk5JKyTblBS
        TSekpNNafeXwr/aP8BfGvxzocV3c614e8cTvo6aRY6hILhGvLXXLQlLDVYWEF1FF
        o5u8Wkjo4iVz5ZkO0p4L2dO7hKUY/Wbyg5QjfW0qnM76TaUanLyxlaKtKx5s6kp2
        9nGnOMXF0/enKrTSmoJSmuazjUg03eSs77S1/Tz9o/SraPQzbEssI0ObDk8749Rt
        2jclgRhmxuz8xVjxxXl4WmlVlG1+ad4y5lrUjFv3Ltezs3GPPfl2WjDGWlQjGXs2
        uWcVz6JSklJzi5J80lq+df8AgKZzEjvdePDcnrdeBPD7Af3UhnmVRuGCeWOCcZH4
        VtSUlKclbmUmoU5S5Vyv4py15Y1FJSjza80Xa+qtxSlUmlCC9rtOUtXypr3Ir7XK
        0rxV7buyvr/fLql1NZWjtbDE7KwSV4zMlqgXMl1NEuC6RgEpFuHmybU6Fs+HfVJW
        lru9kr/E3e/lvfve51vVPlS00tb34236LV7LutWfgL/wUh/as0j4V6ZeaOoW68Za
        jdSaj4jvtRukmm8K+G4Igug+HNodQmueJ52Gr3lvCytFaFF8srFErbUcPGdZtQ55
        yjGMHJuMIe8m37q1lO/M9rKPvWSTOzCYepXm405Pki+ZzUbxqN6OnJ7pUrv2kmly
        t3T11/k5+Kv7X3xW8f6tdyR+L7rw3plnJdz2+k+GpH06yt0maVZfs8VibeP7RNkN
        cHDSO7PMxZgCfeoYGhG0pU26l0lUqXk5Xk1GcIO6UHZrdqK1k1qdihTm6nvc0HaM
        ZSalzacvNZPm992jTas+Varmlc+SfEXx1+LOvpfaM/ijUbzS5Io7YR3l9cMY1aEw
        mIXUjMV3F3e5diWMQYuRXo0sLRpNSlKpSvOMb2U4tptznGFufktu42jbVO+hw+/U
        nfDyhVqSVSXPKo4uTUbQVOUlanOm46Ra5nG99Txy58UzWy6f4d8PhLuJjdx3Wp3M
        sgSWaMsZZrctveGNrh3FodpZ4UL/AMSPWlPCvn9tJp0qEOWFK6b+J+zbtq0knObb
        15knq5RVYmo4fu6dKpKTVKmsTzNRUXRvUioxvZq1ua7cdtbtmZNbppcgurm9e61B
        JI5jOkcm8LwXj89pFxEVLIyqCdp+prerXdWUY0ozk7Ri72jzVWrRk73tzvmlK/xO
        NkugqFKST5rwclHkam4VJxuvZwdWLbhVScvaxu3ODWibOD8RXFvPcy3SRyv5pRiw
        kRU3hie5YllUDdn5iVBJGWNc8IyjP2bqc1vZ3k0nJVJt88ZSStO+vMlpGNm9Yo7a
        NFJU4wp8k4zlKMoq8Ek1ze0k5KTbjJ1Fu27Xt04e4uYBuDWMs+Sf3jzgMhBySiLz
        z6DcDjJ71C53GUZ8lOpGUHeKbs1p7SmpL3Iz0crXTs29gnSpOGHcW6i9pOPtJ1ox
        mp87cans7y0kpN3urq/vN6GC8oknZbWUxysu5YZS0LytwfKjeP8AdSTN1XcQGxtJ
        ya6FTk8PHltP2lSTk3Zt+zb/AHsIS2jCS5pyVtW9NbGOInGnKc4LX2M7yVSKhTi5
        Knyqk3KM5yl7715UrzbT0HaB4l1Pw/rum63YXnkanoup6dq+nSqXt5BdaXfwXkMM
        7piRUleAwTFgxMMjjkkApxlVqTU3GVL2E4uycYv2r96d3dt2UWvd5VJpa2PPUfZU
        48k5UqijzynTaqWvJK0YTVpOS1mo/FH4JKTUj+jOw/ab8AftLfBxPE/hXUJP7d0P
        w1PpXjnwvqIWLXvDGvI1rLLFdxbsXVpPxdadqduZbS+tpIZ4JpY3R3+erYNYPE+z
        rqLUnGdCsnUnRr0JTbVSEml7Sm2m5RlGnUjKMqVWFOpCcVtKEqtOk23Kc7ynQnyK
        pTq8rjJSWsItXuoK8XdauLTfsunI/wDwkegvIfnn+HmnFsk5PlXgwxLd2D5Iye/p
        ztyNQkuWDlUmpc95SS01jHl0UWo8zaTvLTmOOkkoX57WveDg7y1strSfKlpd2au0
        lon/AHW/FL4neFfhl8O/FPj7XL+0k03w9pMtz5EFzBc3GoXzgR6dptpb28kk1zc3
        t48cMNvGGeQtwO9eHTg5zUFdtXdnpaK96/ot76ve12bq6aSb9+SitnzTnta6++7S
        S1uj+An9vn4reNvjh8UNf1rVzNA02s6gul6B5ymVpprhxd6hcSKTHLJbuIre4uzv
        FusEenWzZ87f9BhoU4c0otRi0lOc7RalJe7PldnzNJ04u9oq0pJ6292hhJrkVJuH
        N7OcqUnKEOWEeaLv2cuZyvpVdtH7p+XHjqxHh2R9DWRJryMBtUu1YjM8iBmhXjJM
        QyMjkZAHWujD4ieIbmoyUVKdHC07Le9p1G/d9rFy0Ssoxcr3dzWvQhTn7OLU6tWn
        L2klKnDlc0pU4r3eVVOX3uWLc4ScVo3deFeNbl7Gwg0yDzEmuVT7QsRIk/epvKEg
        53GPAdid2WI+voYeDlOM6kpVPZv2bly2lNU1zScd1aDio8ikrqL1Z5nJKnGnQSXN
        KU6rvSU5xk4tRble86inu4vV35+5zfh3SvEEIW6gt5QXmYJINhaFWQYSNZQRv8pO
        CRtCgsxycHjxGOw8eX3ofv3NzlGLaclLlkpzbTUYX5rfYtZOVj0MPl+MrOUoN+zX
        LJ1ZSXtJKVO1VxhJKMXeNnDTVu8m1rS8WeIm08yW0EV5PemMJLdXr4sgygkIiLhm
        cE/fRVQg7c9qKU4VZQpxkkl8dRPmlUVrqDn0ure9Jtxutm3eJYT6vGf7ppu1Rqqn
        TjSSinUjTjL3/aTdmqmqtaKeyfiMmsahLOzMWjEiSq8JP7hiyszPHuP3gwznr2zj
        Nd1JRlKq7VYJRXNOajL2b2i1yNqV2/ek0276nHzum6dWTk4TdSTg1zPmatz2bv7X
        l0lFJLkW3NdHMPfXkE26N5WVW3MquzSoG/i2k/NEwzsIJwMgjIzURVSVKo1q413T
        ipc8ZTjKKXKpON9Wm5LZvqtjBqKrwuouFR+0tK007T97WN5xjOMlywWi1adyUanN
        LEzNJuO4kMgBkBHJUgrw69RnHJyCetEEnJSqJR5I1FQjFt+03cpSjtytppcrbnbW
        K1CTqJ1JSUPZU1UnS5IptKprdpJ+64RtyS5nJ2k03cqzX6yMG5kZhvkL5WYMflDb
        uA4c4JPJByCM4IdFRhUvKPNGdOVOnJc3snFzu1NfE1CWnSTbdhzlGahKE5e1bU6s
        ly05wjypUmpNKKhNavSy5ZJK709L+G/xT1b4Y+IB4g0qeeOOawex1yxQvnUtB3rN
        d2yxKSJnhAeW1DBm88+Wu0yljnXpe3tCrdJVHNSU6bXPNcsEubmcITfKp9eSN5Xs
        c06lqVKNWblOU60qXLKUn+65FVq01o7TSUXvJtr4mop/08+GNZsvEU3gHW9LuEu9
        P1j4ZWV9Y3aYMdzaTTWcsEqtn72HZZO+8HgV4tanVhVdn7OpKKhZqyXLzTcdLJW3
        dna767KYxlL2krQcItKUXpJyls07ptK0uZvVyeyPp9/jnd+NYl0cWniS1jg868u2
        ufE095YKqxMkEjRCQGKWNyWi2KZC4HlnOTX3fE/A+K4cwVPG1cbgcRFy5FChQqUq
        s73le7heLgmnu43ut7Hz3B2eYXPcXiMNRo1oThDmnGpKFRRU7JRvCc+Wo0pc0Vfm
        1s0kz84fGeuaRf8AjjUHjUvFpEUl7qUsrLMTHYM7Q2f338mEzsHEQJE05kedmYNX
        57TpVadGVTRzlP2eHU3H2bqT3qy3d7Sk1FvWNrWtZfp81CnLmnBr2SjVvZOV7ckq
        fIvf5NEotRUJO9rq7Pz/APFrHWvE4vpGUw3+p3VxKxw5jhSZpGLAZGWUCLKkbQF6
        9/boUlByjytyowVpOSk5ztq3rbm9rFz3+HlSTZwN1JVdmrVp1fcXNGN07axV7zvy
        813y2945XS/BT+LvEK3c6kwzXVxIgKDYIt3lR844wijg4I9c5r5/O87eEp06Uajj
        UT5rpctOcpq7k5t3XI20tr35db3PqMhyKWIq0p1Ie1u3JtS5lBzSblzSXNzwdo2u
        tN03dn1Pofwu06KDS1ktUkia6ltiwAWNJ5ISsKsSC2WKFUfcQGO09efzzF5picRR
        xC5pc84SqS0c6c023VUYuVlKS5dPikuZraz+6o5fRpVKK5KcruajGrFOnGcre/e9
        +aXvWb92Gm7kjxP4x/Bi3CSmKz+ZRnd5GCsbHD4YfMCjHA3jJBJ5xx15FnmJo4ih
        CvWqOUf3dWnLkm4LaknZ2/eJR99qTUdH8TSnOcojVjWUadLllCUaj5E5WUo88k+W
        XI5SatNbJJLY+PNY+HUunwbhbsEHnLyPnRlyrMuQSOADhuuT16193gM6pVKtSnKq
        6aVSMnyqzV1OV0lJRnyT+O94vTl1PgsdkkfYJUoNy96dROKc1OkuRWbVruO0U7zf
        MtU9fINR0aKIGRIx+5ynmE4ZAxI2nABKg8jP3eRkgjP0vtGlGcKiqzmlGzb1lNJp
        crdqT+J635tLPmbPlp0bzpxUILk53JunFc8WrqCWnPJTTqNRS5GpRdtE+IuYZEkJ
        25fA3FRgSBeByMLx/CSAeTnvVXiqbbcLxTinK3JTim272u7xd0+S3Nd6nPVU+ZSj
        Q91xlz1IqSjJr3nGCv7mt5O90tUndleTy2Ql1EcoIOVHJOeSf9k9HbGeCQPVXnOD
        jTXtHVp+0VRSs6cE3LlinJe9dRbb1cfdumVP2dT2anaFTmpx+FcraWkJybu043UX
        KUrtx6iNJ5kII2rdWz74uhBjBOQG5Zg4zjhsZyTnirXM0480UmoylNqDV5WvNpNK
        DXwxUU3te2rMqlJKFOmmoumpulFr3aUJ9dLyfPJ+42npzNtbr+iP/glX4j0v4mfA
        278NeIfE8Gkap8Gtbn8I6HDeyWKG98G6xY2Gu6K9s97dQzvFpkd2NGYRlot+nMzf
        PmorYKpj3OrTp4ip7b3pxo03XcK0ZyVXZNw9pPmqqDV1TqLlXs7SPKxmJpYebdSl
        CnGSjK7qqEZzcY86S5Y3ip8zhGFuSKSldO7991nxhceCtB1jU4eJNkq+bjzCqzQN
        CPKUbf3jF1jhlJKw5JHzdP2rxSwvtMrwsoOdOk68YTdKej5XKpCN5J6TqRXNFOPM
        1ZM/PPCarSp5ri42cq0qFadOLhyzm+VRvKMWrqNOb5JNq1mm73Ph7XfEEum+FPEl
        +2+K81ydbVnkKidoxE0mwN94ICSzjAMjtuHv+A1OWeJo0+ZWhz14wU+Vxk02/dkr
        TjL4Wt272euv79BtYSpLSEJNUakEuaUYtpRU3q3bePK3bWzufM5u2FzplsHZ5Ht5
        IVkRgoWSUIZXz1yWdgGbIzwOld06llXrJWitVFWXuqPLzW2spLV2V03JIwpYaFHE
        JRdRKU0owUnJODavZytGf8yTu0292tfrn4Y+DWlktybckRwRSEKMGRsjAJ5BIJ3s
        B97oRivxPiXMXVm6aSkm6lNu7cYQslfkumoN3UWrtSs9Uj9hyTBxowpq8IqS9tG3
        NzT5Xzezk9bVFHW32rtNvp9Yr4Na40iWyZfs4uwXt2jTHkXSkS2c0eR8jiZXB3ck
        lVw26vjvrk6XJOnOUnSbj7K/8aHwTV1KKk40+WLk7O6el7s9+dBVJ1Kcla65ueN7
        05yvODta2uja2e1k9vK/EOgPqBlt763Ine2awuep230YZydpw37xgGTd/AxCklaX
        1qVKrz0byVOopxlPmc1D4ZRrSi2+ZXat0Su7XNHhK1SNpQcn7PlcL2vNqWk3vGE0
        n7/w81mtdT5M8VeB/Pjnjmi8s3KusDBcsLuJW8yNicZ8+MKVLDJaMqOa+lw+auDp
        VKM415Uq0ZRdTm5XTirqSVm6cVNyUnquV3WrucNTK41Yyi3Tp+11bhD2jVWFlOnJ
        Ws/aQaXM3bnvJNM+EfiF4OutEnedgfJfcJ8KQqo2SJAv8SHIy3JV9wx0FfqOU53S
        xdKpQbpq96sU00/axaTSqO7jODd4R2lFRfe/5PnuSeyrwqwpSahGdKUnem6cHK6n
        yWalTc2o83xTcZOR4NqMaoJY2z0CjGc9e2ewz83dw3r1+jjU9pSgoyvH2c5NwULv
        3ruVW/vcslaygtHe9tl8tjIcrqU4wqpKmlZPllKN/ecaTT1jr1vNOLtojlJH+9GD
        tYAMrkZwSMMrE8kEdSecAEZOK30vVnZtypyjaael583Iqe3z63T2R51SN5ckYSp0
        7eydTktySteXOpa3cVLWztqo3W2V55iYSKz7NxDEj7oXIdGT9FwMFfmBPNb3gqdP
        lhJtyhH2bfxpt8sra3Su7vpbe4k4SjCVKVWrKcpc0lD3WoRcY81/e5W0oxT+xd2b
        Pvn9hq+STxP4z0OZ4nSXRdN1CKKRElA2XVynmRFlYplCI3UHoFPIIr9v8EMSljuI
        MFGdNSlh8Di6tJylFpudahTqvT3nKFJ0ub4n7KSlpY/H/FzC1a2X5VWm1L2GJxNO
        MnCMqadeFCq0qkbO/uN+za05lK71R+wfxDljl8K6mZmJhiSKWZSOCkcyyFUyM4du
        mSeOM+n0niPhoz4OzWXJGXsnQrJSesvZ1ottyj71kn7yja+19XfyPDSs6fFmU041
        JU/avEYevTnGLc41KV5RlF+81rFwalKSSsk7tv4I8aaq8mmX/mGR9140u0H5RIuY
        0QZO0kBtzdDtAyK/lScJutS5XHmcHCTqShCS5eZyppa3hUV+VWurXT3a/qblpxdZ
        SclFScqdppylJOKk7RdrLRp2023emP8ABPwdqPxJ8cy2trG09hpI8yWZU/dK+0N5
        bNgABzkDnO4ZPJBry+Jcwhl2B5HZSxCUZxs4uKXM1KMYtylNrlilF8sVyu19/WyL
        ASxmKniL0pUqFkr2ahVhPmUIOSspSUneXvNNcjSP090ZPCfgi7trDX3t9GRbdLhp
        tTuYrR53XCtHbRHczwYAUliGkJyF5zX4/isvxuLXt24ym+Zvkf7tXSkn1bqSatJP
        RSvtc/QYZnRw1Rxp+41Hk5pSjzTlJ2vzbWbulJP5XZ7fY+NPh9r1m6WWpWMy7VUi
        3niZ13/6tlCMxBVlVlPGGXd3rw8Vk+Io2co1UpTcuZ2j8be7Sb5ZXcYuN7Oze56e
        HxaqOPK0mk5XevPa/wAV9WtV0crPXojgtZ0bSLxnni8t3Z4pVljdn/eW/wAiyO33
        syA71Jy6s0ikkcnljyU/3NWU5KcHFp68koW+05J6xVpy1Tspb7/QYaNapSmlKDk4
        3apy+ODSlyyvf3VdpR0cVZW3T8d8a+B7OYTeWix/aVS63gLvinRt8cvPLDO+JwuC
        VdcjIydaNdqpGcYup7KrB8jvFKM3blsrc0JQS5nsmu7D2ElFQUXF1H3Sk5rlk7yt
        pC1rJ9mt9D4w+KPw6tdTsbiKaIJcfvTGRgqPN+WQ9w8Ln5hG3zRsc9a+jyjG4jDV
        eaEklWlz3jrJXldqKktLxsn1UG7XumfPZzgaOJTdRRnWhSq2kvdvGTs4TVls23fd
        OKa0bZ+X3j/wvfeGtSuNOulJMLv5Ei5K3FscjAKniSEkAqxztKtk9K/Y8sx313DT
        rU3S5KUJOpGXN+7lKalKMErtxna9mmlN7o/Hc5wiw0+RKXMuaLvdyrL4eZ2tKFSG
        mt7WfM7pniMtwwmI3/MP3W5hjgcq5+oOHJJ4Hqc17TnUbpSVOlG6drSnL2l3u9br
        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
        aCrxjzSVk0qjUuaXwO6lLRavRO0tT5LifKKWLw1Ss+eUqVCcusXZWktFHm92d1dX
        ut73uflFayqtnHM77I1jWaaWTk+WYfPEjKQQCq59xjoK/ZJSlSnVnOMVSpVKspPm
        ilywbTkk7OztaUbuy7I/GYwlOEMPUc3VnHklXlUTTrya5XKCT9nNSfJ7rbU5aaan
        6rfsn+Erjwp8PZ7/AFFGiv8AxNqb6w8LAAG1khjj09thxiRbJIkdsjOzHtX9WeGG
        SLJcgk6y5cTmOJnmFZ88U/38UqEZOKWlHDQhQp8y5nCnFtyd5S/nfxBzSOZZjQp0
        5OrSwWFpYOLalKHPC86zp2XLarVcqzjdtSk5dWl+ijXW8YJIJ44YkEkYJLfeU/xE
        DpzyCSK+1lBcqlZwhFyatFSil1S1sr6vtrrd6HxUdYqSleEZSSSk6clKcVZ1He7T
        abnFNqLTtrY8X/Y80GTWP+CiHwl1GeGQ6VovxVW8hSThGW08MeIbcbz0fdPdi5Gf
        4hE4JKhq/gXxLthcPxDFU6cIVswxd5ST5+V42nUpOLeqqKcZyvazpyld7H9++F0J
        VKuCqTqPkq5VgacVK05xlFYeUlBu8ox93R813eSTd2f12fF/xJoUcbx20i3E3lgG
        Rc5D7vmGGJJCknbzx1xzX8w1eWpP93OOnM5O2t07XXLZ62ly2tq77n9BqnyQvFc0
        km7r3ndPzWj1SltreS3PzB+Kdu/iK8ubWznkhZ3Ia5kCsgBO0cYJ+XJCovPcjrUt
        S54SjJppqzjZNud1zc0vecoxd9E9EuY5oLklzpc0580pcqtJxWrvK/w31i30va5L
        8KPhD4S0C8+0zldX1mcjz76fafLLYb7NbrgrEmeZCMMxxnNa1KtoqkqtWWqjyylz
        RqcsbKTeqvd3jtaS0WujvUrJPEpqkvepx5W0tbe9JPmV2rp66PV2dj628d6Tpdh8
        PbyOGNLV5omIRMRt8qfKuBgFVJ5xwQT17+Vi50IU2p2am+ZRjG8pOLv2tLXmlLdb
        LWwYKNWpJShdQippaXfLZrlaldy7NOybTadz8d/FtrbN4ovI7g7nMweFmAO11GAq
        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
        GMq1WUZQ/je5Wre9LXmt73NGL0btre7a5brw34i/tUazr2qz3vin4VXXhSPTIZr3
        T9OhtdYt5vPUYRbqdrcQ3LbCEdYgQFHGTmuitwtXq+ylHGxjVrQjCUaLlOnGM7KK
        qxk01Ti9XOUVaLTk0ke5F4PCRdL6jifYzd5Tq0aVX31DnjFulVq8sKiuqSTd9Iys
        7X+cLT4+6NqGoGXUtM1Pw9cTzPLDftb3s2mhp8mQPPLAgh6Ko3fIxGAeoHo0OHsX
        haKpaYmiqalWkp06tWMYu0mp/btO8VJLSNm07o+czCrhFUdSlCtl14zfJNSjZuN4
        OEG9FJe84pt8zau2emaB8V7jX2+z/bLK9gkWRIJ7XDHCMQRIASrFxglkOVYDAzmv
        MxuVYaDlVpUpTrTqOnKlLRxSdko3kk483v3atbmSPMwuYYqeIjRrt1JNOTnD94tr
        wXxfxeRvmmrpXSbTRpajrTPbzh2OyNNw3YxzwwzkZ4GefQ5z3MJBVW+fki4Sk+WW
        0aUG1dxW/LJ3VtUnzS8rzDEcv7ypBNWnyt8keWHMrPXeSntupS3vc+XdQ8TW891q
        17aPHcN9tex2hg3k+SBmOTklHfcrAEchgR8uM/379HzA4fLeCq+MXs62IzTH4meI
        qRcXWw6wMpYejSqLRyvyznGL+zLn5j+K/HLMp43iXL6M41vY0cKqeFclKPtpV6k3
        iJOSuofAqN7JqcVDmeqPOdU1m7uN++Uxo33VQlVAA7884/iyOowM1+sV8TXrzklV
        XsqtZRag1yexXvOM3rZyd4yafdb7/k+FwdOlyv2Uuaqq84rlu/ZXvOEocyTcZp3l
        O1/s32PP7y9LFiZcnI3PuYHcST2H3cDGTk5ri9tF1KnMoQpXs6VSL5Xyu0ZKTd20
        3a/u3T+C92epUUZU+aUr1py5Z1ItztNcr5aiStZU0kmtOay1aufogbhQCflBAOMH
        P3TkkknGBkBmGQcZ7E19tTqX1fNzR55TXuzbV37tn9lauyV4rz1Pm4LnqTSU+e8K
        dRStJKXJG6c2rJttcrV1FX0s7nlXxm+K6/C/wrDPZtBN4q12GYaDbyKJbfTbNPll
        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
        G0QGmBW+Ltp+Xw==
        =fUJB
        -----END PGP PUBLIC KEY BLOCK-----

    2. Run: gpg --import

        And paste public key content

        and press Ctrl + D

    3. Verify tags

        git tag -l   List all Tags

        git tag -v <tag name>      Verify Tag content.
